package com.stylefeng.guns.api.film.VO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConditionListInfo<T> implements Serializable {
    List<T> catInfo;
    List<T> sourceInfo;
    List<T> yearInfo;

    public List<T> getCatInfo() {
        return catInfo;
    }

    public void setCatInfo(List<T> catInfo) {
        this.catInfo = catInfo;
    }

    public List<T> getSourceInfo() {
        return sourceInfo;
    }

    public void setSourceInfo(List<T> sourceInfo) {
        this.sourceInfo = sourceInfo;
    }

    public List<T> getYearInfo() {
        return yearInfo;
    }

    public void setYearInfo(List<T> yearInfo) {
        this.yearInfo = yearInfo;
    }
}
