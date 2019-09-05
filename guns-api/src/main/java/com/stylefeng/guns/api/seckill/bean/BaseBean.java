package com.stylefeng.guns.api.seckill.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class BaseBean implements Serializable {
    private String cinemaAddress;
    private Integer cinemaId;
    private String cinemaName;
    private String description;
    private Date endTime;
    private String imgAddress;
    private BigDecimal price;
    private Date startTime;
    private Integer status;
    private Integer stock;
    private Integer uuid;
}
