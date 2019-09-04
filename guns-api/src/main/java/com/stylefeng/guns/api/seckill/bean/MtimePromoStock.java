package com.stylefeng.guns.api.seckill.bean;


import java.io.Serializable;

public class MtimePromoStock implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer uuid;
    /**
     *  秒杀活动id
     */
    private Integer promoId;
    /**
     * 库存
     */
    private Integer stock;


    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "PromoStock{" +
        "uuid=" + uuid +
        ", promoId=" + promoId +
        ", stock=" + stock +
        "}";
    }
}
