package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaInfo implements Serializable {

    private int uuid;

    private String cinemaName;

    private String address;

    private int minimumPrice;
}
