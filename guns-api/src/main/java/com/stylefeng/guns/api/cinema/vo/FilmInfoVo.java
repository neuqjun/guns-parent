package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmInfoVo implements Serializable {
    private String filmId;
    private String filmName;
    private String filmType;
    private String imgAddress;
    private String filmCats;
    private String filmLength;

}
