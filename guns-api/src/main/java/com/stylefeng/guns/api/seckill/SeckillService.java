package com.stylefeng.guns.api.seckill;

import com.stylefeng.guns.api.seckill.vo.PromoResponseVo;
import com.stylefeng.guns.api.seckill.vo.PromoVO;

import java.util.List;

public interface SecKillService {

    PromoResponseVo getPromo(Integer brandId);

    String initPromoStockLog(Integer promoId, Integer amount);

    Boolean transactionSavePromoOrder(Integer promoId, Integer userId, Integer amount, String stockLogId);

    void savePromoOrder(Integer promoId, Integer amount, Integer userId, String stockLogId);

    Boolean publishPromoStock(Integer cinemaId);

    List<PromoVO> getPromoByCinemaId(Integer cinemaId);
}
