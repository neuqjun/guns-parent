package com.stylefeng.guns.api.seckill.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class PromoDataVo implements Serializable {

    private static final long serialVersionUID = 4366431476031029985L;

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
