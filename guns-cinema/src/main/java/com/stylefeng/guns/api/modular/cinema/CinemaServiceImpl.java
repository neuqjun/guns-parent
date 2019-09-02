package com.stylefeng.guns.api.modular.cinema;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.cinema.service.CinemaService;
import com.stylefeng.guns.api.cinema.vo.*;
import com.stylefeng.guns.api.common.persistence.dao.MtimeCinemaTMapper;
import com.stylefeng.guns.api.common.persistence.dao.MtimeFieldTMapper;
import com.stylefeng.guns.api.common.persistence.dao.MtimeHallFilmInfoTMapper;
import com.stylefeng.guns.api.common.persistence.model.MtimeCinemaT;
import com.stylefeng.guns.api.common.persistence.model.MtimeFieldT;
import com.stylefeng.guns.api.common.persistence.model.MtimeHallFilmInfoT;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = CinemaService.class)
public class CinemaServiceImpl implements CinemaService{

    @Resource
    private MtimeCinemaTMapper cinemaTMapper;

    @Resource
    private MtimeHallFilmInfoTMapper mtimeHallFilmInfoTMapper;

    @Resource
    private MtimeFieldTMapper fieldMapper;



    //获取播放场次的信息
    @Override
    public FieldDataVo getFieldInfo(String cinemaId) {
        FieldDataVo fieldDataVo = new FieldDataVo();
        CinemaInfoVo cinemaInfo = getCinema(cinemaId);
        FilmList filmLists = getFilm(cinemaId);

        fieldDataVo.setCinemaInfoVo(cinemaInfo);
        fieldDataVo.setLists(filmLists);
        return fieldDataVo;

    }


    //根据影院id查询影院信息
    private CinemaInfoVo getCinema(String cinemaId) {
        MtimeCinemaT mtimeCinemaT = cinemaTMapper.selectById(cinemaId);
        CinemaInfoVo cinemaInfoVo =new CinemaInfoVo();
        cinemaInfoVo.setCinemaId(mtimeCinemaT.getUuid());
        cinemaInfoVo.setCinemaName(mtimeCinemaT.getCinemaName());
        cinemaInfoVo.setCinemaPhone(mtimeCinemaT.getCinemaPhone());
        cinemaInfoVo.setImgUrl(mtimeCinemaT.getImgAddress());
        cinemaInfoVo.setCinemaAddress(mtimeCinemaT.getCinemaAddress());
        return cinemaInfoVo;
    }


    //根据影院id查询电影清单
    private FilmList getFilm(String cinemaId) {
        FilmList filmList =new FilmList();

        FilmFields filmFields =new FilmFields();
        MtimeFieldT mtimeFieldT = fieldMapper.selectById(cinemaId);
        filmFields.setBeginTime(mtimeFieldT.getBeginTime());
        filmFields.setEndTime(mtimeFieldT.getEndTime());
        filmFields.setHallName(mtimeFieldT.getHallName());
        filmFields.setPrice(mtimeFieldT.getPrice()+"");
        MtimeHallFilmInfoT mtimeHallFilmInfoT = mtimeHallFilmInfoTMapper.selectById(cinemaId);
        filmFields.setLanguage(mtimeHallFilmInfoT.getFilmLanguage());
        filmFields.setFieldId(mtimeHallFilmInfoT.getUuid()+"");
        List<FilmFields> lists =new ArrayList<>();
        lists.add(filmFields);

        filmList.setFilmFields(lists);
        filmList.setFilmId(mtimeHallFilmInfoT.getFilmId()+"");
        filmList.setFilmName(mtimeHallFilmInfoT.getFilmName());
        filmList.setFilmLength(mtimeHallFilmInfoT.getFilmLength());
        filmList.setFilmCats(mtimeHallFilmInfoT.getFilmCats());
        filmList.setActors(mtimeHallFilmInfoT.getActors());
        filmList.setImgAddress(mtimeHallFilmInfoT.getImgAddress());

        return filmList;

    }



    //响应报文需返回的Data数据
    @Override
    public DataVo getDataInfo(String cinemaId, int fieldId) {
        DataVo dataVo =new DataVo();
        FilmInfoVo filmInfoVo = getFilmInfo(cinemaId,fieldId);
        CinemaInfoVo cinemaInfoVo =getCinemaInfo(cinemaId,fieldId);
        HallInfoVo hallInfoVo =getHallInfo(cinemaId,fieldId);

        dataVo.setFilmInfoVo(filmInfoVo);
        dataVo.setCinemaInfoVo(cinemaInfoVo);
        dataVo.setHallInfoVo(hallInfoVo);

        return dataVo;

    }


    //根据放映场次查询播放的电影编号，然后根据电影编号获取对应的电影信息
    private FilmInfoVo getFilmInfo(String cinemaId, Integer fieldId) {
        FilmInfoVo filmInfoVo =new FilmInfoVo();
        MtimeHallFilmInfoT mtimeHallFilmInfoT = mtimeHallFilmInfoTMapper.selectByFilmId(fieldId);
        filmInfoVo.setFilmId(mtimeHallFilmInfoT.getFilmId()+"");
        filmInfoVo.setFilmName(mtimeHallFilmInfoT.getFilmName());
        filmInfoVo.setFilmType(mtimeHallFilmInfoT.getFilmLanguage());
        filmInfoVo.setFilmCats(mtimeHallFilmInfoT.getFilmCats());
        filmInfoVo.setFilmLength(mtimeHallFilmInfoT.getFilmLength());
        return filmInfoVo;
    }


    //根据影院编号，获取影院信息
    private CinemaInfoVo getCinemaInfo(String cinemaId, int fieldId) {
        MtimeCinemaT mtimeCinemaT = cinemaTMapper.selectByUUId(cinemaId);
        CinemaInfoVo cinemaInfoVo =new CinemaInfoVo();
        cinemaInfoVo.setCinemaId(mtimeCinemaT.getUuid());
        cinemaInfoVo.setCinemaAddress(mtimeCinemaT.getCinemaAddress());
        cinemaInfoVo.setImgUrl(mtimeCinemaT.getImgAddress());
        cinemaInfoVo.setCinemaPhone(mtimeCinemaT.getCinemaPhone());
        cinemaInfoVo.setCinemaName(mtimeCinemaT.getCinemaName());
        return cinemaInfoVo;
    }



    //根据放映场次ID获取放映信息
    private HallInfoVo getHallInfo(String cinemaId, int fieldId) {
        HallInfoVo hallInfoVo =new HallInfoVo();
        MtimeFieldT mtimeFieldT = fieldMapper.selectById(fieldId);
        hallInfoVo.setHallFieldId(mtimeFieldT.getHallId()+"");
        hallInfoVo.setHallName(mtimeFieldT.getHallName());
        hallInfoVo.setPrice(mtimeFieldT.getPrice()+"");
        return hallInfoVo;
    }
}
