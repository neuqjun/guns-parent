package com.stylefeng.guns.api.controller;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.api.common.persistence.dao.MtimePromoMapper;
import com.stylefeng.guns.api.common.persistence.dao.MtimePromoOrderMapper;
import com.stylefeng.guns.api.common.persistence.dao.MtimePromoStockMapper;
import com.stylefeng.guns.api.common.persistence.dao.MtimeStockLogMapper;
import com.stylefeng.guns.api.common.persistence.model.MtimePromo;
import com.stylefeng.guns.api.common.persistence.model.MtimePromoOrder;
import com.stylefeng.guns.api.common.persistence.model.MtimeStockLog;
import com.stylefeng.guns.api.mq.ProducerMQ;
import com.stylefeng.guns.api.seckill.SecKillService;
import com.stylefeng.guns.api.seckill.vo.PromoDataVo;
import com.stylefeng.guns.api.seckill.vo.PromoResponseVo;
import com.stylefeng.guns.api.util.DateUtil;
import com.stylefeng.guns.core.constant.StockLogStatus;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.exception.GunsExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Slf4j
@Component
@Service(interfaceClass = SecKillService.class)
public class SecKillServiceImpl implements SecKillService {

    @Resource
    MtimePromoMapper mtimePromoMapper;

    @Resource
    MtimeStockLogMapper stockLogMapper;

    @Resource
    private ProducerMQ producerMQ;

    @Resource
    MtimePromoStockMapper mtimePromoStockMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource
    private MtimePromoOrderMapper promoOrderMapper;

    private static final Integer EXCHANGE_CODE_VALID_MONTH = 3;

    @Override
    public PromoResponseVo getPromo(Integer brandId) {
        List<PromoDataVo> promoDataVoList = mtimePromoMapper.queryPromoList(brandId, new Date());
        PromoResponseVo  promoResponseVo = new PromoResponseVo();
        promoResponseVo.setImgPre("");
        promoResponseVo.setMsg("");
        promoResponseVo.setStatus(0);
        promoResponseVo.setTotalPage("");
        promoResponseVo.setData(promoDataVoList);
        promoResponseVo.setNowPage("");
        return promoResponseVo;
    }

    //初始化流水，存入数据库
    @Override
    public String initPromoStockLog(Integer promoId, Integer amount) {
        MtimeStockLog mtimeStockLog = new MtimeStockLog();
        mtimeStockLog.setPromoId(promoId);
        mtimeStockLog.setAmount(amount);
        UUID uuid = UUID.randomUUID();
        mtimeStockLog.setUuid(uuid.toString());
        mtimeStockLog.setStatus(StockLogStatus.INIT.getIndex());
        Integer insert = stockLogMapper.insert(mtimeStockLog);
        if(insert > 0) {
            return uuid.toString();
        } else {
            return null;
        }
    }

    //生成订单
    @Override
    public Boolean transactionSavePromoOrder(Integer promoId, Integer userId, Integer amount, String stockLogId) {
        //调用mq producer
        Boolean ret = producerMQ.transactionSaveOrder(promoId, userId, amount, stockLogId);
        return ret;
    }

    //完成本地事务中的操作：订单入库和扣减redis缓存
    @Override
    public void savePromoOrder(Integer promoId, Integer amount, Integer userId, String stockLogId) {
        //参数校验
        processParam(promoId,userId,amount);
        //根据促销电影票uuid查询电影信息
        MtimePromo promo = mtimePromoMapper.selectById(promoId);
        //生成订单数据并存入数据库
        MtimePromoOrder promoOrder = insertPromoOrder(promo, userId, amount);
        //扣减redis缓存
        Boolean ret = decreaseStock(promoId, amount);
        if(!ret) {//扣减库存失败
            MtimeStockLog mtimeStockLog = stockLogMapper.selectById(stockLogId);
            mtimeStockLog.setStatus(StockLogStatus.FAIL.getIndex());//修改流水状态
            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("uuid",stockLogId);
            Integer update = stockLogMapper.update(mtimeStockLog, entityWrapper);
            throw new GunsException(GunsExceptionEnum.STOCK_ERROR);
        }
        MtimeStockLog mtimeStockLog = stockLogMapper.selectById(stockLogId);
        mtimeStockLog.setStatus(StockLogStatus.SUCCESS.getIndex());
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("uuid",stockLogId);
        Integer update = stockLogMapper.update(mtimeStockLog, entityWrapper);
        if(update <= 0) {//更新流水失败
            //回滚redis
            increaseStock(promoId, amount);
            throw new GunsException(GunsExceptionEnum.STOCK_ERROR);
        }

        //返回前端
    }


    //参数校验
    private void processParam(Integer promoId, Integer userId, Integer amount) {
        if (promoId == null) {
            log.info("promoId不能为空！");
            throw new GunsException(GunsExceptionEnum.REQUEST_NULL);
        }
        if (userId == null) {
            log.info("userId不能为空！");
            throw new GunsException(GunsExceptionEnum.REQUEST_NULL);
        }
        if (amount == null) {
            log.info("amount不能为空！");
            throw new GunsException(GunsExceptionEnum.REQUEST_NULL);
        }
    }

    //生成秒杀订单
    private MtimePromoOrder insertPromoOrder(MtimePromo promo,Integer userId, Integer amount){
        MtimePromoOrder promoOrder = buidPromoOrder(promo,userId,amount);
        Integer insertRet = promoOrderMapper.insert(promoOrder);
        if (insertRet < 1) {
            log.info("生成秒杀订单失败！promoOrder:{}", JSON.toJSONString(promoOrder));
            return null;
        }
        return promoOrder;
    }

    private MtimePromoOrder buidPromoOrder(MtimePromo promo, Integer userId, Integer amount) {
        MtimePromoOrder promoOrder = new MtimePromoOrder();
        String uuid = UUID.randomUUID().toString();
        Integer cinemaId = promo.getCinemaId();
        String exchangeCode = UUID.randomUUID().toString();
        //兑换开始时间和兑换结束时间 为从现在开始，到未来三个月之内
        Date startTime = new Date();
        Date endTime = DateUtil.getAfterMonthDate(EXCHANGE_CODE_VALID_MONTH);
        BigDecimal amountDecimal = new BigDecimal(amount);
        BigDecimal price = amountDecimal.multiply(promo.getPrice()).setScale(2, RoundingMode.HALF_UP);
        promoOrder.setUuid(uuid);
        promoOrder.setUserId(userId);
        promoOrder.setCinemaId(cinemaId);
        promoOrder.setExchangeCode(exchangeCode);
        promoOrder.setStartTime(startTime);
        promoOrder.setEndTime(endTime);
        promoOrder.setAmount(amount);
        promoOrder.setPrice(price);
        promoOrder.setCreateTime(new Date());
        return  promoOrder;
    }

    //减少库存
    public Boolean decreaseStock(Integer promoId, Integer amount) {
        try {
            Long decrease = redisTemplate.opsForValue().increment("promo1", amount * -1);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    //增加库存
    public Boolean increaseStock(Integer promoId, Integer amount) {
        try {
            Long increment = redisTemplate.opsForValue().increment("promo1", amount);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
