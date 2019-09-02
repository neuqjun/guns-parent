package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RespVo implements Serializable {

    private static final long serialVersionUID = 2633390433272171401L;

    private int status;

    private String msg;
}
