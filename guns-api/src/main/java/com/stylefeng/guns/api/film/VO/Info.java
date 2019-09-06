package com.stylefeng.guns.api.film.VO;

import java.io.Serializable;

public class Info implements Serializable {
    String biography;
    Actorss actors;
    ImgVO imgVO;

    public ImgVO getImgVO() {
        return imgVO;
    }

    public void setImgVO(ImgVO imgVO) {
        this.imgVO = imgVO;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Actorss getActors() {
        return actors;
    }

    public void setActors(Actorss actors) {
        this.actors = actors;
    }
}
