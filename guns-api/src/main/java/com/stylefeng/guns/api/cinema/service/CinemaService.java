package com.stylefeng.guns.api.cinema.service;

import com.stylefeng.guns.api.cinema.vo.DataVo;
import com.stylefeng.guns.api.cinema.vo.FieldDataVo;

import java.util.Map;

public interface CinemaService {

    public DataVo getDataInfo(String cinemaId, int fieldId);

    public FieldDataVo getFieldInfo(String cinemaId);

    Map<String, Object> getCinemasListInfo(Integer brandId, Integer districtId, Integer hallType, Integer pageSize, Integer nowPage);

    Map<String, Object> getConditionInfo(Integer brandId, Integer hallType, Integer areaId);
}
