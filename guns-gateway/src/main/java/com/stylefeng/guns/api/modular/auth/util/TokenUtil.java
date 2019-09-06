package com.stylefeng.guns.api.modular.auth.util;

import com.stylefeng.guns.api.config.properties.JwtProperties;
import com.stylefeng.guns.api.seckill.vo.UserLoginInfoVo;
import com.stylefeng.guns.core.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TokenUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;
    //userId在redis中的有效时间
    private static final Integer expireTime = 60 * 60;

    public UserLoginInfoVo getUserId(HttpServletRequest request, HttpServletResponse response) {
        //从请求头Authorization中获取token,jwtProperties约定了请求头的字段
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        String userId = null;
        if(requestHeader != null && requestHeader.startsWith("Bearer")) {
            authToken = requestHeader.substring(7);//获取authtoken
            //从token中获取userId
            try {//token是否过期和签名在访问gateway是已经验证
                userId = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (Exception e) {
                e.printStackTrace();
                throw new SystemException("服务器错误");
            }
            if(StringUtils.isBlank(userId)) {
                return null;
            } else {
                //将userId存入缓存
                redisTemplate.opsForValue().set(authToken, userId, expireTime, TimeUnit.SECONDS);
            }
            return UserLoginInfoVo.success(userId);
        }
        return null;
    }
}
