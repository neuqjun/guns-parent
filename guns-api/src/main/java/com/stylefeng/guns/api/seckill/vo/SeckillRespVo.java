package com.stylefeng.guns.api.seckill.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class SeckillRespVo implements Serializable {

    private Integer status;

    private String msg;

    private Map<String, Object> data;

}
