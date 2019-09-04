package com.stylefeng.guns.api.modular.film;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.stylefeng.guns.api.common.persistence.dao.*;
import com.stylefeng.guns.api.common.persistence.model.MtimeActorT;
import com.stylefeng.guns.api.common.persistence.model.MtimeCatDictT;
import com.stylefeng.guns.api.common.persistence.model.MtimeSourceDictT;
import com.stylefeng.guns.api.common.persistence.model.MtimeYearDictT;
import com.stylefeng.guns.api.film.FilmService;
import com.stylefeng.guns.api.film.VO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Service(interfaceClass = FilmService.class)
public class FilmServiceImpl implements FilmService {
    @Autowired
    MtimeFilmTMapper mtimeFilmTMapper;
    @Autowired
    MtimeCatDictTMapper mtimeCatDictTMapper;
    @Autowired
    MtimeSourceDictTMapper mtimeSourceDictTMapper;
    @Autowired
    MtimeYearDictTMapper mtimeYearDictTMapper;
    @Autowired
    MtimeActorTMapper mtimeActorTMapper;
    @Override
    public ArrayList<FilmT> queryFilmList(FilmRequst filmRequst) {
        String catId;
        if(filmRequst.getCatId() == 99){
            catId = null;
        }else {
            catId = "%#" + filmRequst.getCatId() + "#%";
        }
        PageHelper.startPage(filmRequst.getOffset(),filmRequst.getPageSize());
        ArrayList<FilmT> filmTS = mtimeFilmTMapper.queryFilmList(catId,filmRequst.getShowType(),filmRequst.getSourceId(),filmRequst.getYearId(),filmRequst.getSortId());
        return filmTS;
    }

    @Override
    public int countFilm(FilmRequst filmRequst) {
        String catId = "%#" + filmRequst.getCatId() + "#%";
        int count = mtimeFilmTMapper.countFilm(catId,filmRequst.getShowType(),filmRequst.getSourceId(),filmRequst.getYearId());
        return count;
    }

    @Override
    public ConditionListInfo getConditionList(ConditionList conditionList) {
        ConditionListInfo conditionListInfo = new ConditionListInfo();
        List<MtimeCatDictT> mtimeCatDictTS;
        if(conditionList.getCatId() == 99){
            EntityWrapper<MtimeCatDictT> wrapper = new EntityWrapper();
            mtimeCatDictTS = mtimeCatDictTMapper.selectList(wrapper);
        }else {
            EntityWrapper<MtimeCatDictT> wrapper = new EntityWrapper();
            wrapper.eq("UUID",conditionList.getCatId());
            mtimeCatDictTS = mtimeCatDictTMapper.selectList(wrapper);
        }
        conditionListInfo.setCatInfo(mtimeCatDictTS);
        List<MtimeSourceDictT> mtimeSourceDictTS;
        if(conditionList.getSourceId() == 99){
            EntityWrapper<MtimeSourceDictT> wrapper = new EntityWrapper();
            mtimeSourceDictTS = mtimeSourceDictTMapper.selectList(wrapper);
        }else {
            EntityWrapper<MtimeSourceDictT> wrapper = new EntityWrapper();
            wrapper.eq("UUID",conditionList.getSourceId());
            mtimeSourceDictTS = mtimeSourceDictTMapper.selectList(wrapper);
        }
        conditionListInfo.setSourceInfo(mtimeSourceDictTS);
        List<MtimeYearDictT> mtimeYearDictTS;
        if(conditionList.getYearId() == 99){
            EntityWrapper<MtimeYearDictT> wrapper = new EntityWrapper();
            mtimeYearDictTS = mtimeYearDictTMapper.selectList(wrapper);
        }else {
            EntityWrapper<MtimeYearDictT> wrapper = new EntityWrapper();
            wrapper.eq("UUID",conditionList.getYearId());
            mtimeYearDictTS= mtimeYearDictTMapper.selectList(wrapper);
        }
        conditionListInfo.setYearInfo(mtimeYearDictTS);
        return conditionListInfo;
    }

    @Override
    public DetailData searchById(String id) {
        DetailData detailData = new DetailData();
        detailData = mtimeFilmTMapper.searchById(id);
        String filmCats = detailData.getFilmCats();//影片类型
        String substring = filmCats.substring(1, filmCats.length()-1);
        String[] split = substring.split("#");
        String[] showName = mtimeCatDictTMapper.selectShowNameById(split);
        filmCats = "";
        for (String s : showName) {
            filmCats = filmCats + s + ",";
        }
        filmCats = filmCats.substring(0, filmCats.length() - 1);
        detailData.setInfo01(filmCats);
        int filmArea = detailData.getFilmArea();
        int filmSource = detailData.getFilmSource();
        MtimeSourceDictT mtimeSourceDictT = mtimeSourceDictTMapper.selectById(filmArea);
        MtimeSourceDictT mtimeSourceDictT1 = mtimeSourceDictTMapper.selectById(filmSource);
        int filmLength = detailData.getFilmLength();
        detailData.setInfo02(mtimeSourceDictT.getShowName() + ", " +mtimeSourceDictT1.getShowName() + "/" + filmLength);
        Date filmTime = detailData.getFilmTime();
        detailData.setInfo03(filmTime + " " + mtimeSourceDictT.getShowName() + "上映");
        String filmImgs = detailData.getFilmImgs();
        String[] split1 = filmImgs.split(",");
        detailData.getImgVO().setImg01(split1[0]);
        detailData.getImgVO().setImg02(split1[1]);
        detailData.getImgVO().setImg03(split1[2]);
        detailData.getImgVO().setImg04(split1[3]);
        return detailData;
    }

    @Override
    public DetailData searchByName(String id) {
        DetailData detailData = new DetailData();
        detailData = mtimeFilmTMapper.searchByName(id);
        String filmCats = detailData.getFilmCats();//影片类型
        String substring = filmCats.substring(1, filmCats.length()-1);
        String[] split = substring.split("#");
        String[] showName = mtimeCatDictTMapper.selectShowNameById(split);
        filmCats ="";
        for (String s : showName) {
            filmCats = filmCats + s + ",";
        }
        filmCats = filmCats.substring(0, filmCats.length() - 1);
        detailData.setInfo01(filmCats);
        int filmArea = detailData.getFilmArea();
        int filmSource = detailData.getFilmSource();
        MtimeSourceDictT mtimeSourceDictT = mtimeSourceDictTMapper.selectById(filmArea);
        MtimeSourceDictT mtimeSourceDictT1 = mtimeSourceDictTMapper.selectById(filmSource);
        int filmLength = detailData.getFilmLength();
        detailData.setInfo02(mtimeSourceDictT.getShowName() + ", " +mtimeSourceDictT1.getShowName() + "/" + filmLength);
        Date filmTime = detailData.getFilmTime();
        detailData.setInfo03(filmTime + " " + mtimeSourceDictT.getShowName() + "上映");
        String filmImgs = detailData.getFilmImgs();
        String[] split1 = filmImgs.split(",");
        detailData.getImgVO().setImg01(split1[0]);
        detailData.getImgVO().setImg02(split1[1]);
        detailData.getImgVO().setImg03(split1[2]);
        detailData.getImgVO().setImg04(split1[3]);
        return detailData;
    }
}
