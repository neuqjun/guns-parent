package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FilmList implements Serializable {
    private String actors;
    private String filmCats;
    private List<FilmFields> filmFields;
    private String filmId;
    private String filmLength;
    private String filmName;
    private String filmType;
    private String imgAddress;

}
