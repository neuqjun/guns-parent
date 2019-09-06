package com.stylefeng.guns.core.constant;

public enum PromoStatus {

    ING(1, "正在促销");

    PromoStatus(Integer index, String msg) {
        this.index = index;
        this.msg = msg;
    }

    private Integer index;

    private String msg;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
