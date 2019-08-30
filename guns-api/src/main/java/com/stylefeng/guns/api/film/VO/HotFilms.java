package com.stylefeng.guns.api.film.VO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HotFilms implements Serializable {
    private int filmNum;
    private List<FilmInfo> filmInfo;
}
