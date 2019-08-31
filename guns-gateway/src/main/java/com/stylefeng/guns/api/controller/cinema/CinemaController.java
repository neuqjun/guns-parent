package com.stylefeng.guns.api.controller.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.cinema.service.CinemasService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("cinema")
@RestController
public class CinemaController {

    @Reference(interfaceClass = CinemasService.class, check = false)
    CinemasService cinemasService;

    @RequestMapping(value = "getCinemas", method = RequestMethod.GET)
    public Map<String, Object> getCinemasList(Integer brandId, Integer districtId, Integer hallType, Integer pageSize, Integer nowPage) {
        Map<String, Object> cinemasResultMap = cinemasService.getCinemasListInfo(brandId, districtId, hallType, pageSize, nowPage);
        return cinemasResultMap;
    }

    @RequestMapping(value = "getCondition", method = RequestMethod.GET)
    public Map<String, Object> getCondition(Integer brandId, Integer hallType, Integer areaId) {
        Map<String, Object> cinemaCondition = cinemasService.getConditionInfo(brandId, hallType, areaId);
        return cinemaCondition;
    }
}
