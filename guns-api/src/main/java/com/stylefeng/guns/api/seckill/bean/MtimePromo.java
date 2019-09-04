package com.stylefeng.guns.api.seckill.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class MtimePromo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer uuid;
    /**
     * 秒杀活动影院id
     */
    private Integer cinemaId;
    /**
     * 秒杀价格
     */
    private BigDecimal price;
    /**
     * 秒杀开始时间
     */
    private Date startTime;
    /**
     * 秒杀结束时间
     */
    private Date endTime;
    /**
     * 秒杀活动状态
     */
    private Integer status;
    /**
     * 活动描述
     */
    private String description;


    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Promo{" +
        "uuid=" + uuid +
        ", cinemaId=" + cinemaId +
        ", price=" + price +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", status=" + status +
        ", description=" + description +
        "}";
    }
}
