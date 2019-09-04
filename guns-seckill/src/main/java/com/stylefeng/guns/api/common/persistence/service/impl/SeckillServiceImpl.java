package com.stylefeng.guns.api.common.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.common.persistence.dao.MtimePromoMapper;
import com.stylefeng.guns.api.seckill.SeckillService;
import com.stylefeng.guns.api.seckill.bean.MtimePromo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service(interfaceClass = SeckillService.class)
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private MtimePromoMapper mtimePromoMapper;

    @Override
    public List<MtimePromo> selectAllPromo() {
        List<MtimePromo> mtimePromos = mtimePromoMapper.selectAllPromo();
        for (MtimePromo mtimePromo : mtimePromos) {
            long time = mtimePromo.getStartTime().getTime();


        }
        return null;
    }
}
