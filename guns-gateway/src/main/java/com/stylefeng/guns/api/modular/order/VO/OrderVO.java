package com.stylefeng.guns.api.modular.order.VO;

import com.stylefeng.guns.api.order.VO.OrderRequest;

public class OrderVO {
    int status;
    String msg;
    OrderRequest data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public OrderRequest getData() {
        return data;
    }

    public void setData(OrderRequest data) {
        this.data = data;
    }
}
