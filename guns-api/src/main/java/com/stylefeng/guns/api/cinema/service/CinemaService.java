package com.stylefeng.guns.api.cinema.service;

import com.stylefeng.guns.api.cinema.vo.CinemaConditionVo;
import com.stylefeng.guns.api.cinema.vo.CinemaListVo;
import com.stylefeng.guns.api.cinema.vo.DataVo;
import com.stylefeng.guns.api.cinema.vo.FieldDataVo;

public interface CinemaService {

    DataVo getDataInfo(String cinemaId, int fieldId);

    FieldDataVo getFieldInfo(String cinemaId);

    CinemaListVo getCinemasListInfo(Integer brandId, Integer areaId, Integer hallType, Integer pageSize, Integer nowPage);

    CinemaConditionVo getConditionInfo(Integer brandId, Integer hallType, Integer areaId);
}
