package com.stylefeng.guns.api.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.api.common.persistence.model.MtimeUserT;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author jun
 * @since 2019-08-28
 */
public interface MtimeUserTMapper extends BaseMapper<MtimeUserT> {
    MtimeUserT selectByUsername(@Param("username") String username);

    MtimeUserT selectByUsernameAndPassword(@Param("username") String username,@Param("password") String password);
}
