package com.stylefeng.guns.api.common.exception;

/**
 * 影院异常枚举
 */
public enum CinemaExceptionEnum {

    QUERY_ERROR(1, "影院信息查询失败");

    CinemaExceptionEnum(Integer status, String msg) {
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
