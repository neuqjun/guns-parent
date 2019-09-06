package com.stylefeng.guns.api.seckill.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Data
public class UserLoginInfoVo implements Serializable {

    private static final long serialVersionUID = -7735471906402863323L;

    private Integer status;

    private String userId;

    public UserLoginInfoVo() {
    }

    /**
     * 业务成功
     * @param userId
     * @return
     */
    public static UserLoginInfoVo success(String userId) {
        UserLoginInfoVo userLoginInfoVo = new UserLoginInfoVo();
        userLoginInfoVo.setUserId(userId);
        userLoginInfoVo.setStatus(0);
        return userLoginInfoVo;
    }
}
