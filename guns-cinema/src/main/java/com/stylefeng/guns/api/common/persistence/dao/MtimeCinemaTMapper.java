package com.stylefeng.guns.api.common.persistence.dao;

import com.stylefeng.guns.api.common.persistence.model.MtimeCinemaT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 影院信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-08-30
 */
public interface MtimeCinemaTMapper extends BaseMapper<MtimeCinemaT> {
    MtimeCinemaT selectByUUId(@Param("cinemaId") String cinemaId);

}
