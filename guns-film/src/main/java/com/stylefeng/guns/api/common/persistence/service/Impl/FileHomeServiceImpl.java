package com.stylefeng.guns.api.common.persistence.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.common.persistence.dao.MtimeBannerTMapper;
import com.stylefeng.guns.api.common.persistence.dao.MtimeFilmInfoTMapper;
import com.stylefeng.guns.api.film.FilmHomeService;
import com.stylefeng.guns.api.film.VO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service(interfaceClass = FilmHomeService.class)
public class FileHomeServiceImpl implements FilmHomeService {

    @Autowired
    MtimeBannerTMapper mtimeBannerTMapper;
    @Autowired
    MtimeFilmInfoTMapper mtimeFilmInfoTMapper;
    @Override
    public List<BannerVO> getBanners() {
        return mtimeBannerTMapper.getBanners();
    }

    @Override
    public FilmVO getHotFilms(boolean isLimit, int nums) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfoList = mtimeFilmInfoTMapper.getHotFilms();
        int filmNum = filmInfoList.size();
        filmVO.setFilmInfo(filmInfoList);
        filmVO.setFilmNum(filmNum);
        return filmVO;
    }

    @Override
    public FilmVO getSoonFilms(boolean isLimit, int nums) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfoList = mtimeFilmInfoTMapper.getSoonFilms();
        int filmNum = filmInfoList.size();
        filmVO.setFilmInfo(filmInfoList);
        filmVO.setFilmNum(filmNum);
        return filmVO;
    }

    @Override
    public List<FilmInfo> getBoxRanking() {
        List<FilmInfo> filmInfoList = mtimeFilmInfoTMapper.getBoxRanking();
        return filmInfoList;
    }

    @Override
    public List<FilmInfo> getExpectRanking() {
        List<FilmInfo> filmInfoList = mtimeFilmInfoTMapper.getExpectRanking();
        return filmInfoList;
    }

    @Override
    public List<FilmInfo> getTop() {
        List<FilmInfo> filmInfoList = mtimeFilmInfoTMapper.getTop();
        return filmInfoList;
    }
}
