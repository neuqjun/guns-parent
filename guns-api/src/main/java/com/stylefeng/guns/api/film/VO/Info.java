package com.stylefeng.guns.api.film.VO;

import java.io.Serializable;

public class Info implements Serializable {
    String biography;
    Actorss actors;


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
