package com.stylefeng.guns.api.seckill.vo;

public enum ResponseStatus {

    SUCCESS(0, "业务成功"),

    FAIL(1, "业务失败"),

    EXPIRE(2, "Token过期");

    ResponseStatus(Integer index, String description) {
        this.index = index;
        Description = description;
    }

    private Integer index;

    private String Description;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
