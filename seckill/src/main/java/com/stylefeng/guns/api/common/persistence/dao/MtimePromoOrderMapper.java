package com.stylefeng.guns.api.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.api.common.persistence.model.MtimePromoOrder;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-09-03
 */
public interface MtimePromoOrderMapper extends BaseMapper<MtimePromoOrder> {

    Integer insertPromoOrder(@Param("promoOrder") MtimePromoOrder promoOrder);
}
