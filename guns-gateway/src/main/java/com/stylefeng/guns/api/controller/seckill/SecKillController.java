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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
}
