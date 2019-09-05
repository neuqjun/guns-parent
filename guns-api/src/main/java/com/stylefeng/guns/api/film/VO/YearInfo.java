package com.stylefeng.guns.api.film.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class YearInfo implements Serializable {
    Integer yearId;
    String yearName;
    boolean active = false;

    public YearInfo(Integer yearId, String yearName) {
        this.yearId = yearId;
        this.yearName = yearName;
    }
}
