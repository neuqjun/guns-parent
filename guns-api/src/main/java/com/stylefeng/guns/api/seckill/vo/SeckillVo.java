package com.stylefeng.guns.api.seckill.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SeckillVo implements Serializable {
    private Integer promoId;
    private Integer amount;
    private String promoToken;
}
