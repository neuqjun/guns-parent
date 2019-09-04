package com.stylefeng.guns.api.seckill.bean;

import java.io.Serializable;


public class MtimeStockLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String uuid;
    /**
     * 秒杀活动id
     */
    private Integer promoId;
    /**
     * 数量
     */
    private Integer amount;
    /**
     * 状态
     */
    private Integer status;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StockLog{" +
        "uuid=" + uuid +
        ", promoId=" + promoId +
        ", amount=" + amount +
        ", status=" + status +
        "}";
    }
}
