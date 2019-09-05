package com.stylefeng.guns.api.common.persistence.dao;

import com.stylefeng.guns.api.seckill.bean.BaseBean;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public interface BaseBeanMapper {
    List<BaseBean> selectAllPromo(@Param("promoDate") Date promoDate);
}
