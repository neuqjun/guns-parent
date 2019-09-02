package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmFields implements Serializable {
    private String beginTime;
    private String endTime;
    private String fieldId;
    private String hallName;
    private String language;
    private String price;
}
