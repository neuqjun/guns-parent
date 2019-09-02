package com.stylefeng.guns.api.film.VO;

import java.io.Serializable;
import java.util.List;

public class Actorss<ActorDrtail> implements Serializable {
    DirectorDetail director;
    List<ActorDrtail> actors;


    public DirectorDetail getDirector() {
        return director;
    }

    public void setDirector(DirectorDetail director) {
        this.director = director;
    }

    public List<ActorDrtail> getActors() {
        return actors;
    }

    public void setActors(List<ActorDrtail> actors) {
        this.actors = actors;
    }
}
