package com.stylefeng.guns.api.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.api.common.persistence.model.MtimePromoStock;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-09-03
 */
public interface MtimePromoStockMapper extends BaseMapper<MtimePromoStock> {

    Integer decreaseStock(@Param(value = "promoId") Integer promoId,
                          @Param(value = "amount") Integer amount);
}
