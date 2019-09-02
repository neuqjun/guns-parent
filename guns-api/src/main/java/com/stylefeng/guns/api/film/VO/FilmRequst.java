package com.stylefeng.guns.api.film.VO;

import java.io.Serializable;

public class FilmRequst implements Serializable {
    private Integer showType = 1;
    private Integer sortId = 1;
    private Integer catId = 99;
    private Integer sourceId=99;
    private Integer yearId=99;
    private Integer pageSize=18;
    private Integer offset=0;
    private String kw;

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getYearId() {
        return yearId;
    }

    public void setYearId(Integer yearId) {
        this.yearId = yearId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    @Override
    public String toString() {
        return "FilmRequst{" +
                "showType=" + showType +
                ", sortId=" + sortId +
                ", catId=" + catId +
                ", sourceId=" + sourceId +
                ", yearId=" + yearId +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                ", kw='" + kw + '\'' +
                '}';
    }
}
