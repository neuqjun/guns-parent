package com.stylefeng.guns.api.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.common.exception.BizExceptionEnum;
import com.stylefeng.guns.api.config.properties.JwtProperties;
import com.stylefeng.guns.api.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.api.user.bean.RespBean;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.api.user.vo.UserVO;
import com.stylefeng.guns.api.util.TokenUitls;
import com.stylefeng.guns.core.exception.GunsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class UserController {

    @Reference(interfaceClass = UserService.class,check = false)
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private Jedis jedis;

    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    public RespBean register(String username,String password,String mobile,String email,String address) {
        UserVO userVO = new UserVO();
        userVO.setUsername(username);
        userVO.setPassword(password);
        userVO.setPhone(mobile);
        userVO.setEmail(email);
        userVO.setAddress(address);
        RespBean respBean = userService.insert(userVO);
        return respBean;
    }

    @RequestMapping(value = "/user/check", method = RequestMethod.POST)
    public RespBean check(String  username) {
        RespBean respBean = userService.checkUsername(username);
        return respBean;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public Map<String, Object> login(String userName, String password) {
        // Boolean validate = userService.selectByUsernameAndPassword(authRequest.getUsername(), authRequest.getPassword());
        HashMap<String, Object> map = new HashMap<>();
        String userId = null;
        userId = userService.login(userName,password);
        int expireSeconds = 3600*6; // 设置 redis中 token过期时间
        if (userId != null) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(userName, randomKey);
            jedis.set(token, userId);
            jedis.expire(token, expireSeconds);
            //return ResponseEntity.ok(new AuthResponse(token, randomKey));
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("randomKey", randomKey);
            map1.put("token", token);
            map.put("status", 0);
            map.put("data", map1);
            return map;
        } else {
            throw new GunsException(BizExceptionEnum.AUTH_REQUEST_ERROR);
        }
    }


    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public RespBean logout(HttpServletRequest request) {
        RespBean respBean = new RespBean();
        try {
            String requestHeader = request.getHeader(jwtProperties.getHeader());
            String authToken = TokenUitls.getToken(request);
            String userToken = jedis.get(authToken);
            if (userToken != null) {
                jedis.del(authToken);
                respBean.setStatus(0);
                respBean.setMsg("成功退出");
                return respBean;
            } else {
                respBean.setStatus(1);
                respBean.setMsg("退出失败，用户尚未登录");
                return respBean;
            }
        } catch (Exception e) {
            respBean.setStatus(999);
            respBean.setMsg("系统异常，请联系管理员");
            return respBean;
        }
    }

    @RequestMapping(value = "/user/getUserInfo", method = RequestMethod.GET)
    public Map<String, Object> getUserInfo(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            String requestHeader = request.getHeader(jwtProperties.getHeader());
            String authToken = null;
            if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
                authToken = requestHeader.substring(7);
            }
            UserVO userInfo = userService.getUserInfo(authToken);

            if (userInfo != null) {
                map.put("status", 0);
                map.put("data", userInfo);
                return map;
            } else {
                map.put("status", 1);
                map.put("msg", "查询失败，用户尚未登录");
                return map;
            }
        } catch (Exception e) {
            {
                map.put("status", 999);
                map.put("msg", "系统异常，请联系管理人员");
                return map;
            }
        }
    }

    @RequestMapping(value = "/user/updateUserInfo", method = RequestMethod.POST)
    public Map<String, Object> updateUserInfo(UserVO userVO) {
        Map<String, Object> map = userService.updateUserInfo(userVO);
        return map;
    }

}
