package com.stylefeng.guns.api.controller.pay;


import com.alibaba.dubbo.config.annotation.Reference;

import com.stylefeng.guns.api.pay.vo.PayResultVo;
import com.stylefeng.guns.api.util.StorageUtils;
import com.stylefeng.guns.api.pay.vo.OrderPayVo;
import com.stylefeng.guns.api.pay.AliPayServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.util.HashMap;

@RequestMapping("order")
@RestController
public class AliPayController {

    @Reference(interfaceClass = AliPayServiceAPI.class,check = false)
    private AliPayServiceAPI aliPayServiceAPI;

    @Autowired
    private Jedis jedis;

    @RequestMapping(value = "/getPayInfo", method = RequestMethod.POST)
    public OrderPayVo getPayInfo(String orderId) {
        OrderPayVo orderPayVo = new OrderPayVo();
        //根据orderId查询订单总金额等
        String path = aliPayServiceAPI.test_trade_precreate("1",orderId);
        if(path == null) {
            //抛订单支付失败异常
        } else {
            //上传二维码到aliOss
            String QRCodeAddress = StorageUtils.uploadFile(new File(path));
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("orderId", orderId);
            hashMap.put("QRCodeAddress", QRCodeAddress);
            orderPayVo.setData(hashMap);
            orderPayVo.setImgPre("https://molycloud.oss-cn-beijing.aliyuncs.com/");
            orderPayVo.setStatus(0);
        }
        return orderPayVo;
    }

    @RequestMapping(value = "/getPayResult", method = RequestMethod.POST)
    public PayResultVo getPayResult(String orderId, Integer tryNums) {
        String outTradeNo = jedis.get(orderId);
        if(tryNums >= 3) {
            //抛业务异常：订单支付失败，请稍后重试
        }
        PayResultVo payResultVo = aliPayServiceAPI.test_trade_query(outTradeNo, orderId);
        return payResultVo;
    }
}
