package com.stylefeng.guns.api.common.persistence.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.vo.CinemaInfo;
import com.stylefeng.guns.api.common.persistence.model.MtimeCinemaT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 影院信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-08-30
 */
public interface MtimeCinemaTMapper extends BaseMapper<MtimeCinemaT> {

    List<CinemaInfo> selectCinemasList(Page<CinemaInfo> page, @Param("brandId") Integer brandId, @Param("areaId") Integer districtId, @Param("hallId") Integer hallType);
}
