package com.stylefeng.guns.api.order;

import com.stylefeng.guns.api.order.VO.OrderRequest;

public interface OrderService {
    boolean isTrueSeats(int fieldId, int[] soldSeats);

    boolean isSoldSeats(int fieldId, int[] soldSeats);

    OrderRequest creatOrder(int fieldId, int[] soldSeats, String seatsName, Integer userId);

}
