package com.stylefeng.guns.api.order.VO;

import java.io.Serializable;

public class OrderRequest implements Serializable {
    String orderId;
    String filmName;
    String fieldTime;
    String cinemaName;
    String seatsName;
    Double orderPrice;
    String orderTimestamp;

    public OrderRequest(String orderId, String filmName, String fieldTime, String cinemaName, String seatsName, Double orderPrice, String orderTimestamp) {
        this.orderId = orderId;
        this.filmName = filmName;
        this.fieldTime = fieldTime;
        this.cinemaName = cinemaName;
        this.seatsName = seatsName;
        this.orderPrice = orderPrice;
        this.orderTimestamp = orderTimestamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFieldTime() {
        return fieldTime;
    }

    public void setFieldTime(String fieldTime) {
        this.fieldTime = fieldTime;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getSeatsName() {
        return seatsName;
    }

    public void setSeatsName(String seatsName) {
        this.seatsName = seatsName;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(String orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }
}
