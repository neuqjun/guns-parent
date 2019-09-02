package com.stylefeng.guns.api.order;

import com.stylefeng.guns.api.order.VO.OrderVO;

import java.util.List;

public interface OrderService {
    List<OrderVO> getOrderByUserId(Integer userId);

}
