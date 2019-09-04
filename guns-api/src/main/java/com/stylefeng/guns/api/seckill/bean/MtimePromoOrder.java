package com.stylefeng.guns.api.seckill.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng
 * @since 2019-09-03
 */
public class MtimePromoOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String uuid;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 影院id
     */
    private Integer cinemaId;
    /**
     * 兑换码
     */
    private String exchangeCode;
    /**
     * 购买数量
     */
    private Integer amount;
    /**
     * 订单总金额
     */
    private BigDecimal price;
    /**
     * 兑换开始时间
     */
    private Date startTime;
    /**
     * 订单创建时间
     */
    private Date createTime;
    /**
     * 兑换结束时间
     */
    private Date endTime;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "PromoOrder{" +
        "uuid=" + uuid +
        ", userId=" + userId +
        ", cinemaId=" + cinemaId +
        ", exchangeCode=" + exchangeCode +
        ", amount=" + amount +
        ", price=" + price +
        ", startTime=" + startTime +
        ", createTime=" + createTime +
        ", endTime=" + endTime +
        "}";
    }
}
