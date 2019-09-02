package com.stylefeng.guns.core.exception;

/**
 * 影院异常枚举
 */
public enum SystemExceptionEnum {

    SYSTEM_ERROR(999, "系统出现异常，请联系管理员");

    SystemExceptionEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private Integer status;

    private String msg;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
