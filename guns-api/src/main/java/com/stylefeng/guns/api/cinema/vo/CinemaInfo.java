package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaInfo implements Serializable {

    private static final long serialVersionUID = -2798355515525293221L;

    private int uuid;

    private String cinemaName;

    private String cinemaAddress;

    private int minimumPrice;
}
