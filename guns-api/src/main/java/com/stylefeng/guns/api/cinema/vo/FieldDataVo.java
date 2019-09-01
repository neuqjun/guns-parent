package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class FieldDataVo implements Serializable {
    private CinemaInfoVo cinemaInfoVo;
    private FilmList lists;
}
