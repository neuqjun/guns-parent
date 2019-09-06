package com.stylefeng.guns.api.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.api.common.persistence.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-08-23
 */
public interface UserMapper extends BaseMapper<User> {
    @Select("select UUID from mtime_user_t where user_name = #{username}")
    String selectUserIdByUsername(@Param("username") String username);

}