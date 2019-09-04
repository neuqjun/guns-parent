package com.stylefeng.guns.api.pay.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class OrderPayVo implements Serializable {

    private static final long serialVersionUID = 8520147464515636620L;

    private Integer status;

    private String imgPre;

    private Map<String, Object> data;
}
