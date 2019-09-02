package com.stylefeng.guns.api.order.VO;

import lombok.Data;

@Data
public class OrderVO {
    private String orderId;
    private String filmName;
    private String fieldTime;
    private String cinemaName;
    private String seatsName;
    private String orderPrice;
    private String orderStatus;
}
