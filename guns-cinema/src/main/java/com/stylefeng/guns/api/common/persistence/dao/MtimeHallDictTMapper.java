package com.stylefeng.guns.api.common.persistence.dao;

import com.stylefeng.guns.api.cinema.vo.HalltypeInfo;
import com.stylefeng.guns.api.common.persistence.model.MtimeHallDictT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 地域信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-08-30
 */
public interface MtimeHallDictTMapper extends BaseMapper<MtimeHallDictT> {

    List<HalltypeInfo> getHalltypeInfoById(@Param("hallType") Integer hallType);
}
