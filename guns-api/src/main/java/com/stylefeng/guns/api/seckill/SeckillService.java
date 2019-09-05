package com.stylefeng.guns.api.seckill;


import com.stylefeng.guns.api.seckill.vo.SeckillRespVo;
import com.stylefeng.guns.api.seckill.vo.SeckillVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SeckillService {

    SeckillRespVo selectAllPromo();

    SeckillRespVo createPromo(SeckillVo seckillVo, HttpServletRequest request, HttpServletResponse response);

}
