package com.stylefeng.guns.api.pay.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class PayResultVo implements Serializable {

    private static final long serialVersionUID = 5449385441665204277L;

    private Integer status;

    private Map<String, Object> data;
}
