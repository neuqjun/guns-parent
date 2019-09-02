package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;
@Data
public class CinemaConditionVo implements Serializable {

    private static final long serialVersionUID = -7211143274943172805L;

    private Integer status;

    private Map<String, Object> data;
}
