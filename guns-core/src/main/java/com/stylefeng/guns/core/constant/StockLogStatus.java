package com.stylefeng.guns.core.constant;

public enum StockLogStatus {

    SUCCESS(0, "流水更新成功"),

    FAIL(-1, "流水操作失败"),

    INIT(1, "流水初始化")
    ;

    StockLogStatus(Integer index, String mes) {
        this.index = index;
        this.mes = mes;
    }

    private Integer index;

    private String mes;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}
