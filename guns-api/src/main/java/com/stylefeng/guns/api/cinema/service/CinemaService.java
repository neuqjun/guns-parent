package com.stylefeng.guns.api.cinema.service;

import com.stylefeng.guns.api.cinema.vo.DataVo;
import com.stylefeng.guns.api.cinema.vo.FieldDataVo;

public interface CinemaService {

    public DataVo getDataInfo(String cinemaId, int fieldId);

    public FieldDataVo getFieldInfo(String cinemaId);
}
