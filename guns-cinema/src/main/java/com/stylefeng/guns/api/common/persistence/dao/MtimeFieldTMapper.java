package com.stylefeng.guns.api.common.persistence.dao;

import com.stylefeng.guns.api.cinema.vo.HallInfoVo;
import com.stylefeng.guns.api.common.persistence.model.MtimeFieldT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 放映场次表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-08-30
 */
public interface MtimeFieldTMapper extends BaseMapper<MtimeFieldT> {


    String isSelectSeat(int fieldId);
}
