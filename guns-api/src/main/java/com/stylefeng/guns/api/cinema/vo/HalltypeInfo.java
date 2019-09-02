package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class HalltypeInfo implements Serializable {

    private static final long serialVersionUID = 2796407518243534131L;

    private Integer halltypeId;

    private String halltypeName;

    private Boolean isActive;
}
