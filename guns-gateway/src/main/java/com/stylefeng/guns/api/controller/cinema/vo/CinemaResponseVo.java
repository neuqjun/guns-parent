package com.stylefeng.guns.api.controller.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaResponseVo implements Serializable {

    private  Integer status = 0 ;
    private  Object data;
    private  String imgPre = "http://img.meetingshop.cn/";
}
