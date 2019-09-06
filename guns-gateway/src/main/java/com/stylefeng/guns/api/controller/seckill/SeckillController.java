package com.stylefeng.guns.api.controller.seckill;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.modular.auth.util.TokenUtil;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.seckill.SecKillService;
import com.stylefeng.guns.api.seckill.vo.PromoResponseVo;
import com.stylefeng.guns.api.seckill.vo.ResponseStatus;
import com.stylefeng.guns.api.seckill.vo.UserLoginInfoVo;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.exception.GunsExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequestMapping("/promo")
@RestController
public class SecKillController {

    @Reference(interfaceClass = SecKillService.class, check = false)
    private SecKillService secKillService;

    @Reference(interfaceClass = OrderService.class, check = false)
    private OrderService orderService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String PROMOVO_CACHE_KEY_IN_REDIS_PREFIX = "promovo_cache_key_prefix_cinemaid_";

    //发布库存到缓存接口前缀
    private static final String PROMOVO_STOCK_KEY_IN_REDIS_PREFIX = "promovo_stock_key_prefix_cinemaid_";

    @RequestMapping("/getPromo")
    public PromoResponseVo getPromo(Integer brandId, Integer hallType, Integer areaId, Integer pageSize, Integer nowPage) {
        PromoResponseVo promoSecKillVo = secKillService.getPromo(brandId);
        return promoSecKillVo;
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public PromoResponseVo savePromoOrder(@RequestParam(required = true, name = "promoId") Integer promoId,
                                          @RequestParam(required = true, name = "amount") Integer amount,
                                          HttpServletRequest request, HttpServletResponse response) {
        //方法开始执行时间
        long startTime = System.currentTimeMillis();
        //从token中获取userId
        UserLoginInfoVo userLoginInfo = tokenUtil.getUserId(request, response);
        String userId = userLoginInfo.getUserId();
        if(userLoginInfo == null || userLoginInfo.getStatus() != ResponseStatus.SUCCESS.getIndex() || StringUtils.isEmpty(userId)) {
            return PromoResponseVo.expire();//获取userId失败
        }
        if(amount < 0 || amount > 10) {
            return PromoResponseVo.fail("订单数量不合法！");
        }
        try {
            //初始化库存流水，并得到本次操作的流水id，流水中status字段代表订单操作状态
            String stockLogId = secKillService.initPromoStockLog(promoId, amount);
            if (StringUtils.isBlank(stockLogId)) {
                throw new GunsException(GunsExceptionEnum.STOCK_LOG_INIT_ERROR);
            }
            //调用生成订单接口
            Boolean ret = secKillService.transactionSavePromoOrder(promoId, Integer.valueOf(userId), amount, stockLogId);
            if(!ret) {
                throw new GunsException(GunsExceptionEnum.GENERATE_ORDER_ERROR);
            }
        } catch (GunsException e) {
            throw new GunsException(GunsExceptionEnum.DATABASE_ERROR);
        }
        //进行生成订单操作
        long endTime = System.currentTimeMillis();
        log.info("秒杀下单接口耗时 -> {} ms", endTime-startTime);
        return PromoResponseVo.success("下单成功！");
    }

    /**
     * 发布库存到缓存中
     * @param cinemaId
     * @return
     */
    @RequestMapping(value = "/publishPromoStock",method = RequestMethod.GET)
    @ResponseBody
    public PromoResponseVo publishPromoStock(@RequestParam(required = false, name = "cinemaId") Integer cinemaId) {

        String object = (String )redisTemplate.opsForValue().get(PROMOVO_CACHE_KEY_IN_REDIS_PREFIX);
        if (StringUtils.isBlank(object)) {
            Boolean flag = secKillService.publishPromoStock(cinemaId);

            if (flag) {
                log.info("发布库存到缓存中成功~");
                redisTemplate.opsForValue().set(PROMOVO_CACHE_KEY_IN_REDIS_PREFIX,"success");
                redisTemplate.expire(PROMOVO_CACHE_KEY_IN_REDIS_PREFIX,6, TimeUnit.HOURS);
                return PromoResponseVo.success("发布成功！");

            } else {
                return PromoResponseVo.fail("发布失败！");

            }
        }else {
            return PromoResponseVo.success("已经操作过了");
        }

    }
}
