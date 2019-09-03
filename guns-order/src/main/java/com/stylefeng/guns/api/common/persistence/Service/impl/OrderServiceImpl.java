package com.stylefeng.guns.api.common.persistence.Service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.api.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.api.common.persistence.dao.MtimeCinemaTMapper;
import com.stylefeng.guns.api.common.persistence.dao.MtimeFieldTMapper;
import com.stylefeng.guns.api.common.persistence.dao.MtimeFilmTMapper;
import com.stylefeng.guns.api.common.persistence.model.MoocOrderT;
import com.stylefeng.guns.api.common.persistence.model.MtimeCinemaT;
import com.stylefeng.guns.api.common.persistence.model.MtimeFieldT;
import com.stylefeng.guns.api.common.persistence.model.MtimeFilmT;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.order.VO.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    MtimeFieldTMapper mtimeFieldTMapper;
    @Autowired
    MoocOrderTMapper moocOrderTMapper;
    @Autowired
    MtimeCinemaTMapper mtimeCinemaTMapper;
    @Autowired
    MtimeFilmTMapper mtimeFilmTMapper;
    @Override
    public boolean isTrueSeats(int fieldId, int[] soldSeats) {
        String address = mtimeFieldTMapper.isSelectSeat(fieldId);
        Jedis jedis = new Jedis();
        String ids = jedis.get(address);
        ids = "," + ids + ",";
        boolean result = true;
        for (int soldSeat : soldSeats) {
            boolean contains = ids.contains("," + soldSeat + ",");
            if(!contains){
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean isSoldSeats(int fieldId, int[] soldSeats) {
        EntityWrapper<MoocOrderT> entityWrapper = new EntityWrapper();
        entityWrapper.eq("field_id",fieldId);
        List<MoocOrderT> moocOrderTS = moocOrderTMapper.selectList(entityWrapper);
        String seatsIds = ",";
        for (MoocOrderT moocOrderT : moocOrderTS) {
            seatsIds = seatsIds + moocOrderT.getSeatsIds() + ",";
        }
        boolean result = true;
        for (int soldSeat : soldSeats) {
            boolean contains = seatsIds.contains("," + soldSeat + ",");
            if(contains){
                result = false;
            }
        }
        return result;
    }

    @Override
    public OrderRequest creatOrder(int fieldId, int[] soldSeats, String seatsName, Integer userId) {
        EntityWrapper<MtimeFieldT> entityWrapper = new EntityWrapper();
        entityWrapper.eq("UUID",fieldId);
        List<MtimeFieldT> mtimeFieldTS = mtimeFieldTMapper.selectList(entityWrapper);
        Integer cinemaId = mtimeFieldTS.get(0).getCinemaId();
        Integer filmId = mtimeFieldTS.get(0).getFilmId();
        Double price = mtimeFieldTS.get(0).getPrice()*1.0;
        Double orderPrice = soldSeats.length * price;
        String id = UUID.randomUUID().toString().replace("-", "");
        Date date = new Date();
        MoocOrderT moocOrderT = new MoocOrderT();
        moocOrderT.setCinemaId(cinemaId);
        moocOrderT.setFieldId(fieldId);
        moocOrderT.setFilmId(filmId);
        moocOrderT.setOrderPrice(orderPrice);
        moocOrderT.setOrderStatus(0);
        moocOrderT.setSeatsIds(soldSeats.toString().substring(1,soldSeats.toString().length()-1));
        moocOrderT.setOrderTime(date);
        moocOrderT.setSeatsName(seatsName);
        moocOrderT.setUuid(id);
        moocOrderT.setOrderUser(userId);
        moocOrderT.setFilmPrice(price);
        moocOrderTMapper.insert(moocOrderT);
        MtimeCinemaT mtimeCinemaT = mtimeCinemaTMapper.selectByUUId(cinemaId.toString());
        String cinemaName = mtimeCinemaT.getCinemaName();
        MtimeFilmT mtimeFilmT = mtimeFilmTMapper.selectById(filmId);
        String filmName = mtimeFilmT.getFilmName();
        String beginTime = mtimeFieldTS.get(0).getBeginTime();
        String time = String.valueOf(date.getTime());
        OrderRequest orderRequest = new OrderRequest(id, filmName, beginTime, cinemaName, seatsName, orderPrice,time);
        return orderRequest;
    }
}
