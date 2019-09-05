package com.stylefeng.guns.api.common.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.common.persistence.dao.BaseBeanMapper;
import com.stylefeng.guns.api.common.persistence.dao.MtimePromoMapper;
import com.stylefeng.guns.api.seckill.SeckillService;
import com.stylefeng.guns.api.seckill.bean.BaseBean;
import com.stylefeng.guns.api.seckill.bean.MtimePromo;
import com.stylefeng.guns.api.seckill.bean.MtimeStockLog;
import com.stylefeng.guns.api.seckill.vo.SeckillRespVo;
import com.stylefeng.guns.api.seckill.vo.SeckillVo;
import com.stylefeng.guns.api.util.TokenUitls;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.exception.GunsExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Component
@Service(interfaceClass = SeckillService.class)
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private BaseBeanMapper baseBeanMapper;
    @Autowired
    private Jedis jedis;

    @Override
    public SeckillRespVo selectAllPromo() {
        SeckillRespVo seckillRespVo = new SeckillRespVo();
        List<BaseBean> baseBeans = baseBeanMapper.selectAllPromo(new Date());
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", baseBeans);
        seckillRespVo.setImgPre("");
        seckillRespVo.setNowPage("");
        seckillRespVo.setTotalPage("");
        seckillRespVo.setMsg("");
        seckillRespVo.setStatus(0);
        seckillRespVo.setData(map);
        return seckillRespVo;
    }

    @Override
    public SeckillRespVo createPromo(SeckillVo seckillVo, HttpServletRequest request, HttpServletResponse response) {
        SeckillRespVo seckillRespVo = new SeckillRespVo();
        String token = TokenUitls.getToken(request);
        String userId = jedis.get(token);
        if (userId == null) {
            seckillRespVo.setMsg("用户尚未登录，请登录！");
            seckillRespVo.setStatus(1);
            return seckillRespVo;
        }
        if (seckillVo.getAmount() < 0 || seckillVo.getAmount() > 5) {
            seckillRespVo.setStatus(1);
            seckillRespVo.setMsg("抢购数量超出限制，请重新下单");
            return seckillRespVo;
        }

        try {

        } catch (Exception e) {
            throw new GunsException(GunsExceptionEnum.DATABASE_ERROR);
        }

        return null;
    }

    public String initPromoStockLog(SeckillVo seckillVo) {
        Integer amount = seckillVo.getAmount();
        Integer promoId = seckillVo.getPromoId();
        MtimeStockLog mtimeStockLog = new MtimeStockLog();
        mtimeStockLog.setAmount(amount);
        mtimeStockLog.setPromoId(promoId);
        String stockLogId = UUID.randomUUID().toString();
        mtimeStockLog.setUuid(stockLogId);
        return null;

    }
}
