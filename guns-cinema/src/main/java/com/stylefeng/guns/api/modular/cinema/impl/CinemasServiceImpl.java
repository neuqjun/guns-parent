package com.stylefeng.guns.api.modular.cinema.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.service.CinemasService;
import com.stylefeng.guns.api.cinema.vo.AreaInfo;
import com.stylefeng.guns.api.cinema.vo.BrandInfo;
import com.stylefeng.guns.api.cinema.vo.CinemaInfo;
import com.stylefeng.guns.api.cinema.vo.HalltypeInfo;
import com.stylefeng.guns.api.common.persistence.dao.MtimeAreaDictTMapper;
import com.stylefeng.guns.api.common.persistence.dao.MtimeBrandDictTMapper;
import com.stylefeng.guns.api.common.persistence.dao.MtimeCinemaTMapper;

import com.stylefeng.guns.api.common.persistence.dao.MtimeHallDictTMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Service(interfaceClass = CinemasService.class)
public class CinemasServiceImpl implements CinemasService {

    @Autowired
    MtimeCinemaTMapper mtimeCinemaTMapper;

    @Autowired
    MtimeBrandDictTMapper mtimeBrandDictTMapper;

    @Autowired
    MtimeAreaDictTMapper mtimeAreaDictTMapper;

    @Autowired
    MtimeHallDictTMapper mtimeHallDictTMapper;

    @Override
    public Map<String ,Object> getCinemasListInfo(Integer brandId, Integer districtId, Integer hallType, Integer pageSize, Integer nowPage) {
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            Page<CinemaInfo> page = new Page<>(nowPage, pageSize);
            List<CinemaInfo> cinemaInfoList = mtimeCinemaTMapper.selectCinemasList(page, brandId, districtId, hallType);
            long total = page.getTotal();
            if(total <= 0) {
                hashMap.put("status", 1);
                hashMap.put("msg", "影院信息查询失败");
                return hashMap;
            } else {
                hashMap.put("status", 0);
                hashMap.put("nowPage", page.getCurrent());
                hashMap.put("totalPage", page.getPages());
                hashMap.put("data", cinemaInfoList);
                return hashMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("status", 999);
            hashMap.put("msg", "系统出现异常，请联系管理员");
            return hashMap;
        }
    }

    @Override
    public Map<String, Object> getConditionInfo(Integer brandId, Integer hallType, Integer areaId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            List<BrandInfo> brandInfoList = mtimeBrandDictTMapper.getBrandInfoById(brandId);
            List<HalltypeInfo> halltypeInfoList = mtimeHallDictTMapper.getHalltypeInfoById(hallType);
            List<AreaInfo> areaInfoList = mtimeAreaDictTMapper.getAreaInfoById(areaId);
            if(brandInfoList.size() > 0 && halltypeInfoList.size() > 0 && areaInfoList.size() > 0) {
                hashMap.put("status", 0);
                HashMap<String, Object> dataMap = new HashMap<>();
                dataMap.put("brandList", brandInfoList);
                dataMap.put("halltypeInfoList", halltypeInfoList);
                dataMap.put("areaInfoList", areaInfoList);
                hashMap.put("data", dataMap);
                return hashMap;
            } else {
                hashMap.put("status", 1);
                hashMap.put("msg", "影院信息查询失败");
                return hashMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("status", 999);
            hashMap.put("msg", "系统出现异常，请联系管理员");
            return hashMap;
        }
    }
}
