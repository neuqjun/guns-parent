package com.stylefeng.guns.api.common.persistence.dao;

import com.stylefeng.guns.api.common.persistence.model.MtimeHallFilmInfoT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 影厅电影信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-08-30
 */
public interface MtimeHallFilmInfoTMapper extends BaseMapper<MtimeHallFilmInfoT> {

    MtimeHallFilmInfoT selectByFilmId(@Param("filmId") Integer filmId);

}
