package com.stylefeng.guns.api.seckill.vo;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class PromoResponseVo<M> implements Serializable {

    private static final long serialVersionUID = -3841511546051967473L;

    private M data;

    private String imgPre;

    private String msg;

    private String nowPage;

    private String totalPage;

    private Integer status;

    public PromoResponseVo() {
    }

    /**
     * 业务成功
     * @param m
     * @param <M>
     * @return
     */
    public static<M> PromoResponseVo success(M m) {
        PromoResponseVo promoResponseVo = new PromoResponseVo();
        promoResponseVo.setStatus(ResponseStatus.SUCCESS.getIndex());
        promoResponseVo.setData(m);
        return promoResponseVo;
    }

    public static<M> PromoResponseVo expire() {
        PromoResponseVo promoResponseVo = new PromoResponseVo();
        promoResponseVo.setStatus(ResponseStatus.EXPIRE.getIndex());
        promoResponseVo.setMsg(ResponseStatus.EXPIRE.getDescription());
        return promoResponseVo;
    }

    public static<M> PromoResponseVo fail(String msg) {
        PromoResponseVo promoResponseVo = new PromoResponseVo();
        promoResponseVo.setStatus(ResponseStatus.FAIL.getIndex());
        promoResponseVo.setMsg(msg);
        return promoResponseVo;
    }
}
