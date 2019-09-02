package com.stylefeng.guns.api.film;

import com.stylefeng.guns.api.film.VO.*;

import java.util.ArrayList;

public interface FilmService {
    ArrayList<FilmT> queryFilmList(FilmRequst filmRequst);

    int countFilm(FilmRequst filmRequst);

    ConditionListInfo getConditionList(ConditionList conditionList);

    DetailData searchById(String id);

    DetailData searchByName(String id);
}
