package com.stylefeng.guns.api.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.api.common.persistence.model.MtimeStockLog;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-09-03
 */
public interface MtimeStockLogMapper extends BaseMapper<MtimeStockLog> {

    Integer insertStockLog(@Param("stockLog") MtimeStockLog mtimeStockLog);
}
