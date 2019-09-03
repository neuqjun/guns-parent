package com.stylefeng.guns.api.controller.order;

import com.github.pagehelper.PageHelper;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.order.VO.OrderVO;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class Order1Controller {
    @Reference(interfaceClass = OrderService.class ,check = false)
    OrderService orderService;
    @RequestMapping("getOrderInfo")
    public Map<String, Object> getOrderInfo(int nowPage,int pageSize,Integer userId) {
        PageHelper.startPage(nowPage,pageSize);
        Map<String, Object> map = new HashMap<>();
        List<OrderVO> orderList = orderService.getOrderByUserId(userId);
        if(orderList.size()==0){
            map.put("status",1);
            map.put("msg","订单列表为空哦！~");
        }else if(orderList.size()>0){
            map.put("status",0);
            map.put("msg","");
        }else {
            map.put("status",999);
            map.put("msg","系统出现异常，请联系管理员");
        }
        map.put("data",orderList);
        return map;
    }
}
