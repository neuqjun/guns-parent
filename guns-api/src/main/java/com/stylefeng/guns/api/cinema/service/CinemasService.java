package com.stylefeng.guns.api.cinema.service;

import java.util.Map;

public interface CinemasService {

    Map<String, Object> getCinemasListInfo(Integer brandId, Integer districtId, Integer hallType, Integer pageSize, Integer nowPage);

    Map<String, Object> getConditionInfo(Integer brandId, Integer hallType, Integer areaId);
}
