package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ErrorResp implements Serializable {

    private static final long serialVersionUID = 2633390433272171401L;

    public ErrorResp() {
    }

    public ErrorResp(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private Integer status;

    private String msg;
}
