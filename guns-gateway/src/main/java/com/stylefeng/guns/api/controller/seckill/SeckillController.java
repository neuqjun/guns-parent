package com.stylefeng.guns.api.controller.seckill;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.modular.auth.util.TokenUtil;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.seckill.CacheService;
import com.stylefeng.guns.api.seckill.SecKillService;
import com.stylefeng.guns.api.seckill.vo.PromoResponseVo;
import com.stylefeng.guns.api.seckill.vo.PromoVO;
import com.stylefeng.guns.api.seckill.vo.ResponseStatus;
import com.stylefeng.guns.api.seckill.vo.UserLoginInfoVo;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.exception.GunsExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
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
    private RedisTemplate redisTemplate;

    @Autowired
    private CacheService cacheService;

    private static final String PROMOVO_CACHE_KEY_IN_REDIS_PREFIX = "promovo_cache_key_prefix_cinemaid_";

    //发布库存到缓存接口前缀
    private static final String PROMOVO_STOCK_KEY_IN_REDIS_PREFIX = "promovo_stock_key_prefix_cinemaid_";
    //库存售罄标识
    private static final String PROMO_STOCK_INVALID_PROMOID_ = "promo_stock_invalid_promoId_";
    //promoToken 缓存key前缀
    private static final String PROMO_TOKEN_KEY_USERID_ = "promo_token_key_userid_promoid_";

    //库存售罄标记前缀
    private static final String  PROMO_STOCK_CACHE_SELLOUT_PREFIX = "promo_stock_sellout_cache_prefix_";

    //本地缓存前缀
    private static final String PROMOVO_CACHE_KEY_IN_LOCAL_PREFIX = "promovo_local_cache_key_prefix_cinemaid_";

    //秒杀令牌关联key
    private static final String PROMOVO_TOKEN_KEY_PREFIX = "promo_token_key_prefix_";

    //redis缓存过期时间
    private static final Integer REDIS_CACHE_EXPIRE_TIME = 5;



    //根据影院id查询秒杀订单列表
    @RequestMapping("/getPromo")
    public PromoResponseVo getPromo(Integer brandId, Integer hallType, Integer areaId, Integer pageSize, Integer nowPage) {
        PromoResponseVo promoSecKillVo = secKillService.getPromo(brandId);
        return promoSecKillVo;
    }

    /**
     * 根据影院id查询秒杀订单列表
     * @param cinemaId
     * @return
     */
    /*@RequestMapping(value = "/getPromo",method = RequestMethod.GET)
    @ResponseBody
    public PromoResponseVo getPromo(@RequestParam(required = false, name = "cinemaId") Integer cinemaId) {
        List<PromoVO> list = null;
        String localKey = PROMOVO_CACHE_KEY_IN_LOCAL_PREFIX +cinemaId;

        list = (List<PromoVO>) cacheService.getCacheInlocal(localKey);

        if (CollectionUtils.isEmpty(list)) {

            String redisKey = PROMOVO_CACHE_KEY_IN_REDIS_PREFIX + cinemaId;
            list = (List<PromoVO>) redisTemplate.opsForValue().get(redisKey);

            //如果从缓存中取到数据是空
            // 查询数据库 给redis缓存中设值
            if (CollectionUtils.isEmpty(list)) {
                list = secKillService.getPromoByCinemaId(cinemaId);
                redisTemplate.opsForValue().set(redisKey, list);
                redisTemplate.expire(redisKey, REDIS_CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
                cacheService.setCacheInlocal(localKey,list);

                return PromoResponseVo.success(list);
                //如果不为空
            } else {
                cacheService.setCacheInlocal(localKey,list);
                return PromoResponseVo.success(list);
            }
        }else {
            return PromoResponseVo.success(list);
        }

    }*/

    //秒杀订单下单接口
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


    /**
     * 获取 秒杀令牌接口
     * @param promoId
     * @return
     */
    @RequestMapping(value = "/generateToken", method = RequestMethod.GET)
    @ResponseBody
    public PromoResponseVo generateToken(@RequestParam(required = true, name = "promoId") Integer promoId,HttpServletRequest request,HttpServletResponse response) {

        //验证逻辑
        //查看redis中是否又这个缓存售罄的key 如果有 表示库存已经售罄
        String key = PROMO_STOCK_CACHE_SELLOUT_PREFIX + promoId;
        String value = (String) redisTemplate.opsForValue().get(key);

        if (StringUtils.isNotBlank(value)) {
            return PromoResponseVo.fail("获取令牌失败！");
        }

        //先获取userId
        UserLoginInfoVo responseVO = tokenUtil.getUserId(request,response);
        String userId = responseVO.getUserId();

        String token = UUID.randomUUID().toString();
        String tokenKey = PROMOVO_TOKEN_KEY_PREFIX + "userId_" + userId +"_promoId_" +promoId;
        // promo_token_key_prefix_userId_1_promoId_1
        redisTemplate.opsForValue().set(tokenKey,token);
        //redisTemplate.expire(key,5,TimeUnit.MINUTES);

        String tokenAmountkey = "token_amount_key_prefix" + promoId;
        Long tokenAmountRest = redisTemplate.opsForValue().increment(tokenAmountkey, -1);
        /*if (tokenAmountRest < 0 ) {
            return PromoResponseVo.fail("token已经发放完了");
        }*/
        return PromoResponseVo.success(token);
    }
}
