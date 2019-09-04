package com.stylefeng.guns.api.common.persistence.dao;

import com.stylefeng.guns.api.common.persistence.model.MtimeFilmT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.api.film.VO.DetailData;
import com.stylefeng.guns.api.film.VO.FilmT;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * <p>
 * 影片主表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-08-30
 */
public interface MtimeFilmTMapper extends BaseMapper<MtimeFilmT> {
    ArrayList<FilmT> queryFilmList(@Param("catId") String catId,@Param("showType") Integer showType,@Param("sourceId") Integer sourceId,@Param("yearId") Integer yearId,@Param("sortId") Integer sortId);

    int countFilm(@Param("catId") String catId,@Param("showType") Integer showType,@Param("sourceId") Integer sourceId,@Param("yearId") Integer yearId);

    DetailData searchById(String id);

    DetailData searchByName(String id);
}
