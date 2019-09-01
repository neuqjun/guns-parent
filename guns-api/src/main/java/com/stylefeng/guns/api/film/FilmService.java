package com.stylefeng.guns.api.film;

import com.stylefeng.guns.api.cinema.vo.FilmInfoVo;

public interface FilmService {
    FilmInfoVo getFilmInfoByFieldId(String fieldId);
}

