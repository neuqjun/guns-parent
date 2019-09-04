package com.stylefeng.guns.api.common.persistence.dao;

import com.stylefeng.guns.api.cinema.vo.BrandInfo;
import com.stylefeng.guns.api.common.persistence.model.MtimeBrandDictT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 品牌信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-08-30
 */
public interface MtimeBrandDictTMapper extends BaseMapper<MtimeBrandDictT> {

    List<BrandInfo> getBrandInfoById(@Param("brandId") Integer brandId);
}
