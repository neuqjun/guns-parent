package com.stylefeng.guns.api.modular.auth.filter;

import com.stylefeng.guns.api.common.exception.BizExceptionEnum;
import com.stylefeng.guns.api.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.api.config.properties.JwtProperties;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.exception.GunsExceptionEnum;
import com.stylefeng.guns.core.util.RenderUtil;
import io.jsonwebtoken.JwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;
import redis.clients.jedis.Jedis;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 对客户端请求的jwt token验证过滤器
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:04
 */

public class AuthFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private Jedis jedis;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        /*// 获取请求 url
        String servletPath = request.getServletPath();
        // 判断请求 url是否需要鉴权
        String checkPaths = jwtProperties.getCheckPaths();
        String[] split = checkPaths.split(",");
        List<String> strings = Arrays.asList(split);
        if (!strings.contains(servletPath)) {
            chain.doFilter(request, response);
            return;
        }
        // 以下是对需要鉴权的 url进行鉴权(从 request中取出token，到 redis中查询)
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        int expireSeconds = 3600 * 6;
        // 从 request中取出 token
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            try{
                authToken = requestHeader.substring(7);
                // 从 redis中取出userId,判断是否已登录或登录是否过期
                String userId = jedis.get(authToken);
                if (userId == null) {
                    throw new GunsException(GunsExceptionEnum.TOKEN_EXPIRE);
                } else {
                    // 刷新过期时间
                    jedis.expire(authToken,expireSeconds);
                }
            }


            *//* 这一段是判断原来在生成 token时设置的时间期限是否过期
            //验证token是否过期,包含了验证jwt是否正确
            try {
                boolean flag = jwtTokenUtil.isTokenExpired(authToken);
                if (flag) {
                    RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_EXPIRED.getCode(), BizExceptionEnum.TOKEN_EXPIRED.getMessage()));
                    return;
                }
            }*//*
             catch (JwtException e) {
                //有异常就是token解析失败
                RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
                return;
            }
        } else {
            //header没有带Bearer字段
            RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
            return;
        }*/
        chain.doFilter(request, response);
    }
}