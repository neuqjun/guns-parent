package com.stylefeng.guns.api.film.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class CatInfo implements Serializable {
    Integer catId;
    String catName;
    Boolean active = false;

    public CatInfo(Integer catId, String catName) {
        this.catId = catId;
        this.catName = catName;
    }
}
