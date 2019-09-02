package com.stylefeng.guns.api.modular.film.VO;

import com.stylefeng.guns.api.film.VO.DetailData;

public class DetailResult {
    int status;
    String imgPre;
    DetailData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImgPre() {
        return imgPre;
    }

    public void setImgPre(String imgPre) {
        this.imgPre = imgPre;
    }

    public DetailData getData() {
        return data;
    }

    public void setData(DetailData data) {
        this.data = data;
    }
}
