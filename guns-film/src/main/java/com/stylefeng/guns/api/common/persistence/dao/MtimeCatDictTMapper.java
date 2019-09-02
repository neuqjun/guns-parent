package com.stylefeng.guns.api.common.persistence.dao;

import com.stylefeng.guns.api.common.persistence.model.MtimeCatDictT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 类型信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-08-30
 */
public interface MtimeCatDictTMapper extends BaseMapper<MtimeCatDictT> {

    String[] selectShowNameById(@Param("split") String[] split);
}
