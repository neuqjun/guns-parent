package com.stylefeng.guns.api.modular.cinema;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.service.CinemaService;
import com.stylefeng.guns.api.cinema.vo.*;
import com.stylefeng.guns.api.common.persistence.dao.*;
import com.stylefeng.guns.api.common.persistence.model.MtimeCinemaT;
import com.stylefeng.guns.api.common.persistence.model.MtimeFieldT;
import com.stylefeng.guns.api.common.persistence.model.MtimeHallFilmInfoT;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service(interfaceClass = CinemaService.class)
public class CinemaServiceImpl implements CinemaService{

    @Resource
    private MtimeCinemaTMapper cinemaTMapper;

    @Resource
    private MtimeHallFilmInfoTMapper mtimeHallFilmInfoTMapper;

    @Resource
    private MtimeFieldTMapper fieldMapper;

    @Resource
    private MtimeBrandDictTMapper brandDictTMapper;

    @Resource
    private MtimeAreaDictTMapper areaDictTMapper;

    @Resource
    private MtimeHallDictTMapper hallDictTMapper;


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



    @Override
    public Map<String ,Object> getCinemasListInfo(Integer brandId, Integer districtId, Integer hallType, Integer pageSize, Integer nowPage) {
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            Page<CinemaInfo> page = new Page<>(nowPage, pageSize);
            List<CinemaInfo> cinemaInfoList = cinemaTMapper.selectCinemasList(page, brandId, districtId, hallType);
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
            List<BrandInfo> brandInfoList = brandDictTMapper.getBrandInfoById(brandId);
            List<HalltypeInfo> halltypeInfoList = hallDictTMapper.getHalltypeInfoById(hallType);
            List<AreaInfo> areaInfoList = areaDictTMapper.getAreaInfoById(areaId);
            if(brandInfoList.size() > 0 && halltypeInfoList.size() > 0 && areaInfoList.size() > 0) {
                //修改当前选中标签 isActive=true
                for (BrandInfo brandInfo : brandInfoList) {
                    if(brandInfo.getBrandId().equals(brandId) || (brandId == null && brandInfo.getBrandId().equals(99))) {
                        brandInfo.setActive(true);
                    }
                }
                for (HalltypeInfo halltypeInfo : halltypeInfoList) {
                    if(halltypeInfo.getHalltypeId().equals(hallType) || (hallType == null && halltypeInfo.getHalltypeId().equals(99))) {
                        halltypeInfo.setIsActive(true);
                    }
                }
                for (AreaInfo areaInfo : areaInfoList) {
                    if(areaInfo.getAreaId().equals(areaId) || (areaId == null && areaInfo.getAreaId().equals(99))) {
                        areaInfo.setIsActive(true);
                    }
                }
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
