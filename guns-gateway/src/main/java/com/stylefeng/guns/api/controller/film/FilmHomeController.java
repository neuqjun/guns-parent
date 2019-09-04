package com.stylefeng.guns.api.controller.film;


import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.FilmHomeService;
import com.stylefeng.guns.api.film.VO.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/film")
public class FilmHomeController {

    @Reference(interfaceClass = FilmHomeService.class,check = false)
    private FilmHomeService filmHomeService;

    @RequestMapping("getIndex")
    public Map<String, Object> HomePage() {
        boolean isLimit = true;
        int nums= 1;
        Map<String, Object> map = new HashMap<>();
        List<BannerVO> banners = filmHomeService.getBanners();
        FilmVO hotFilms = filmHomeService.getHotFilms(isLimit, nums);
        FilmVO soonFilms = filmHomeService.getSoonFilms(isLimit, nums);
        List<FilmInfo> boxRanking = filmHomeService.getBoxRanking();
        List<FilmInfo> expectRanking = filmHomeService.getExpectRanking();
        List<FilmInfo> top = filmHomeService.getTop();
        FilmIndexVO filmIndexVO = new FilmIndexVO();
        filmIndexVO.setBanners(banners);
        filmIndexVO.setHotFilms(hotFilms);
        filmIndexVO.setSoonFilms(soonFilms);
        filmIndexVO.setBoxRanking(boxRanking);
        filmIndexVO.setExpectRanking(expectRanking);
        filmIndexVO.setTop100(top);
        map.put("data",filmIndexVO);
        map.put("status",0);
        map.put("imgPre","http://img.meetingshop.cn/");
        return map;
    }
}
