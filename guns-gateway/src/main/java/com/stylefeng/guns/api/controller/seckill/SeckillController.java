package com.stylefeng.guns.api.controller.seckill;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.seckill.SeckillService;
import com.stylefeng.guns.api.seckill.vo.SeckillRespVo;
import com.stylefeng.guns.api.seckill.vo.SeckillVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class SeckillController {

    @Reference(interfaceClass = SeckillService.class,check = false)
    private SeckillService seckillService;

    @RequestMapping("/getPromo")
    public SeckillRespVo getPromo() {
        seckillService.selectAllPromo();
        return null;
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public SeckillRespVo createOrder(SeckillVo seckillVo) {
        SeckillRespVo seckillRespVo = new SeckillRespVo();


        return seckillRespVo;
    }

}
