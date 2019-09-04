/*
package com.stylefeng.guns.api.modular.auth.controller;

import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.api.common.exception.BizExceptionEnum;
import com.stylefeng.guns.api.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.api.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.api.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.api.modular.auth.validator.IReqValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

*/
/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 *//*

@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private Jedis jedis;

    @Resource(name = "simpleValidator")
    private IReqValidator reqValidator;

    @RequestMapping(value = "${jwt.auth-path}")
    public ResponseEntity<?> createAuthenticationToken(AuthRequest authRequest) {

        //boolean validate = reqValidator.validate(authRequest);
        Boolean validate = userService.selectByUsernameAndPassword(authRequest.getUsername(), authRequest.getPassword());
        String userId = userService.login(authRequest);
        int expireSeconds = 3600*6; // redis中 token
        if (validate) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(authRequest.getUsername(), randomKey);
            jedis.set(token, userId);
            jedis.expire(token, expireSeconds);
            return ResponseEntity.ok(new AuthResponse(token, randomKey));
        } else {
            throw new GunsException(BizExceptionEnum.AUTH_REQUEST_ERROR);
        }
    }
}
*/
