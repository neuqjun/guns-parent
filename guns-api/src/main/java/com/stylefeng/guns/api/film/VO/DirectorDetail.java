package com.stylefeng.guns.api.film.VO;

import java.io.Serializable;

public class DirectorDetail implements Serializable {
    String imgAddress;
    String directorName;

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }
}
