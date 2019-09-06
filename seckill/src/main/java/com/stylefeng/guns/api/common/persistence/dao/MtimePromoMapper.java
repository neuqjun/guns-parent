package com.stylefeng.guns.api.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.api.common.persistence.model.MtimePromo;
import com.stylefeng.guns.api.seckill.vo.PromoDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-09-03
 */
public interface MtimePromoMapper extends BaseMapper<MtimePromo> {

    List<PromoDataVo> queryPromoList(@Param("brandId") Integer brandId, @Param("nowTime") Date nowTime);
}
