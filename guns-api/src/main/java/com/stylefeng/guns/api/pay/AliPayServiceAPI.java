package com.stylefeng.guns.api.pay;

import com.stylefeng.guns.api.pay.vo.PayResultVo;

public interface AliPayServiceAPI {

    PayResultVo test_trade_query(String outTradeNo, String orderId);

    String test_trade_precreate(String totalAmount, String orderId);
}
