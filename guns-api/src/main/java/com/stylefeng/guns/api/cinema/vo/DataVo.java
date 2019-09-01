package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataVo implements Serializable {

    private FilmInfoVo filmInfoVo;
    private HallInfoVo hallInfoVo;
    private CinemaInfoVo cinemaInfoVo;
}
