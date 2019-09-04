package com.stylefeng.guns.api.common.persistence.dao;

import com.stylefeng.guns.api.common.persistence.model.MoocOrderT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.api.order.VO.OrderVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;


/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
<<<<<<< HEAD
 * @author ll
 * @since 2019-09-02
 */
public interface MoocOrderTMapper extends BaseMapper<MoocOrderT> {

    List<OrderVO> getOrderByUserId(@Param("userId") Integer userId);

}
