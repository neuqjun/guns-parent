package com.stylefeng.guns.api.modular.film;


import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.FilmService;
import com.stylefeng.guns.api.film.VO.*;
import com.stylefeng.guns.api.modular.film.VO.ConditionListResult;
import com.stylefeng.guns.api.modular.film.VO.DetailResult;
import com.stylefeng.guns.api.modular.film.VO.FilmListResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/film")
public class FilmController {

    @Reference
    private FilmService filmServce;

    @RequestMapping("getFilms")
    public FilmListResult getFilms(FilmRequst filmRequst){

        FilmListResult filmListResult = new FilmListResult();
        filmListResult.setImgPre("http://img.meetingshop.cn/");
        filmListResult.setMsg("");
        filmListResult.setNowPage(filmRequst.getOffset());
        filmListResult.setStatus(0);
        int total = filmServce.countFilm(filmRequst);
        ArrayList<FilmT> data = filmServce.queryFilmList(filmRequst);
        filmListResult.setTotalPage(total/(filmRequst.getPageSize()+1));
        filmListResult.setData(data);
        return filmListResult;
    }
    @RequestMapping("getConditionList")
    public ConditionListResult getConditionList(ConditionList conditionList){
        ConditionListResult conditionListResult = new ConditionListResult();
        ConditionListInfo conditionListInfo = filmServce.getConditionList(conditionList);
        conditionListResult.setData(conditionListInfo);
        conditionListResult.setStatus(0);
        return conditionListResult;
    }
    @RequestMapping("films/{id}")
    public DetailResult films(@PathVariable("id") String id,int searchType){
        DetailData detailData =null;
        if(searchType == 0){
            detailData = filmServce.searchById(id);
        }
        if(searchType == 1){
            detailData = filmServce.searchByName(id);
        }
        DetailResult detailResult = new DetailResult();
        detailResult.setStatus(0);
        detailResult.setImgPre("http://img.meetingshop.cn/");
        detailResult.setData(detailData);
        return detailResult;
    }
}
