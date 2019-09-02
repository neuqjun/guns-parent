package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BrandInfo implements Serializable {

    private static final long serialVersionUID = 3695478448011363463L;

    private Integer brandId;

    private String brandName;

    private boolean isActive = false;
}
