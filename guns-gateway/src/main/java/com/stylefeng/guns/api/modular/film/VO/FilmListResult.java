package com.stylefeng.guns.api.modular.film.VO;

import java.util.ArrayList;

public class FilmListResult<T> {
    String imgPre;
    String msg;
    Integer nowPage;
    Integer status;
    Integer totalPage;
    ArrayList<T> data;

    public String getImgPre() {
        return imgPre;
    }

    public void setImgPre(String imgPre) {
        this.imgPre = imgPre;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getNowPage() {
        return nowPage;
    }

    public void setNowPage(Integer nowPage) {
        this.nowPage = nowPage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FilmListResult{" +
                "imgPre='" + imgPre + '\'' +
                ", msg='" + msg + '\'' +
                ", nowPage=" + nowPage +
                ", status=" + status +
                ", totalPage=" + totalPage +
                ", data=" + data +
                '}';
    }
}
