package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class AreaInfo implements Serializable {

    private Integer areaId;

    private String areaName;

    private Boolean isActive;
}
