package com.stylefeng.guns.api.user.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class RespBean implements Serializable {
    /**
     * 状态码
     */
    int status;

    /**
     * 状态信息
     */
    String msg;

}
