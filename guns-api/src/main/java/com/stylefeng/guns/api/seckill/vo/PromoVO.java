package com.stylefeng.guns.api.seckill.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PromoVO implements Serializable {

    private int uuid;

    private BigDecimal price;

    private Date startTime;

    private Date endTime;

    private Integer status;

    private Integer stock;

    private String description;

    private int cinemaId;

    private String cinemaName;

    private String imgAddress;

    private String cinemaAddress;
}
