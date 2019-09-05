package com.stylefeng.guns.api.controller.seckill;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.api.seckill.SeckillService;
import com.stylefeng.guns.api.seckill.vo.SeckillRespVo;
import com.stylefeng.guns.api.seckill.vo.SeckillVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/order")
public class SeckillController {

    @Reference(interfaceClass = SeckillService.class,check = false)
    private SeckillService seckillService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping("/getPromo")
    public SeckillRespVo getPromo() {
        SeckillRespVo seckillRespVo = seckillService.selectAllPromo();
        return seckillRespVo;
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public SeckillRespVo createOrder(SeckillVo seckillVo, HttpServletRequest request, HttpServletResponse response) {
        SeckillRespVo seckillRespVo = seckillService.createPromo(seckillVo,request,response);

        return seckillRespVo;
    }

}
