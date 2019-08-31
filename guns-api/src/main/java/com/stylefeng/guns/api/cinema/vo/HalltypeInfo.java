package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class HalltypeInfo implements Serializable {

    private Integer halltypeId;

    private String halltypeName;

    private Boolean isActive;
}
