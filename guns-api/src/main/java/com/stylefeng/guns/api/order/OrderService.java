package com.stylefeng.guns.api.order;
import com.stylefeng.guns.api.order.VO.OrderRequest;
import com.stylefeng.guns.api.order.VO.OrderVO;

import java.util.List;

public interface OrderService {
    boolean isTrueSeats(int fieldId, int[] soldSeats);

    boolean isSoldSeats(int fieldId, int[] soldSeats);

    OrderRequest creatOrder(int fieldId, int[] soldSeats, String seatsName, Integer userId);

    List<OrderVO> getOrderByUserId(Integer userId);

}
