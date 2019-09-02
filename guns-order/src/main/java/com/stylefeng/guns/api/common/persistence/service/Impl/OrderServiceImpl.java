package com.stylefeng.guns.api.common.persistence.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.order.VO.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    MoocOrderTMapper moocOrderTMapper;
    @Override
    public List<OrderVO> getOrderByUserId(Integer userId) {
        return moocOrderTMapper.getOrderByUserId(userId);
    }
}
