package com.stylefeng.guns.api.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.modular.order.VO.OrderVO;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.order.VO.OrderRequest;

import com.stylefeng.guns.api.util.TokenUitls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("order")
public class OrderController {
    @Reference
    OrderService orderService;
    @Autowired
    private Jedis jedis;
    @RequestMapping("buyTickets")
    public OrderVO buyTickets(int fieldId, int[] soldSeats, String seatsName, HttpServletRequest request){
        OrderVO orderVO = new OrderVO();

        String token = TokenUitls.getToken(request);
        int userId = Integer.parseInt(jedis.get(token));
        boolean isSeats = orderService.isTrueSeats(fieldId,soldSeats);
        if(!isSeats){
            orderVO.setStatus(999);
            orderVO.setMsg("系统出现异常，请联系管理员");
            return orderVO;
        }
        boolean isSold = orderService.isSoldSeats(fieldId,soldSeats);
        if(!isSold){
            orderVO.setStatus(999);
            orderVO.setMsg("系统出现异常，请联系管理员");
            return orderVO;
        }
        orderVO.setStatus(0);
        OrderRequest data = orderService.creatOrder(fieldId,soldSeats,seatsName,userId);
        orderVO.setData(data);
        return orderVO;
    }
}
