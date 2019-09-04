package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class AreaInfo implements Serializable {

    private static final long serialVersionUID = 5614275219262609444L;

    private Integer areaId;

    private String areaName;

    private Boolean isActive = false;
}
