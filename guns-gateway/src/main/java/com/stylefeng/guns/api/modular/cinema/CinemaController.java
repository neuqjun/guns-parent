package com.stylefeng.guns.api.modular.cinema;


import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.cinema.service.CinemaService;
import com.stylefeng.guns.api.cinema.service.CinemasService;
import com.stylefeng.guns.api.cinema.vo.FieldDataVo;
import com.stylefeng.guns.api.modular.cinema.vo.CinemaResponseVo;
import com.stylefeng.guns.api.cinema.vo.DataVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/cinema")
public class CinemaController {

    @Reference(interfaceClass = CinemaService.class,check = false)
    private CinemaService cinemaService;

    @Reference(interfaceClass = CinemasService.class, check = false)
    CinemasService cinemasService;

    @RequestMapping(value = "/getFieldInfo",method = RequestMethod.GET)
    public CinemaResponseVo getDataInfo(@RequestParam(name = "cinemaId",required = true) String cinemaId,
                                        @RequestParam(name = "fieldId",required = true) int fieldId){
        DataVo dataInfo = cinemaService.getDataInfo(cinemaId, fieldId);
        CinemaResponseVo cinemaResponseVo = new CinemaResponseVo();
        cinemaResponseVo.setData(dataInfo);
        return cinemaResponseVo;
    }


    @RequestMapping(value = "/getFields",method = RequestMethod.GET)
    public CinemaResponseVo getFieldInfo(@RequestParam(name = "cinemaId",required = true) String cinemaId){
        CinemaResponseVo cinemaResponseVo = new CinemaResponseVo();
        FieldDataVo fieldInfo = cinemaService.getFieldInfo(cinemaId);
        cinemaResponseVo.setData(fieldInfo);
        return  cinemaResponseVo;
    }



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
