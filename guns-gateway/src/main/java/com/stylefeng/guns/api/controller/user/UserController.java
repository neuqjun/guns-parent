package com.stylefeng.guns.api.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.bean.RespBean;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.api.user.vo.UserVO;
import com.stylefeng.guns.core.util.MD5Util;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping
public class UserController {

    @Reference(interfaceClass = UserService.class,check = false)
    private UserService userService;

    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    public RespBean register(UserVO userVO) {
        String userPwd = userVO.getPassword();
        String encrypt = MD5Util.encrypt(userPwd);
        userVO.setPassword(encrypt);
        RespBean respBean = userService.insert(userVO);
        return respBean;
    }

    @RequestMapping(value = "/user/check", method = RequestMethod.POST)
    public RespBean check(UserVO userVO) {
        RespBean respBean = userService.checkUsername(userVO);
        return respBean;
    }

    /*@RequestMapping(value = "/auth", method = RequestMethod.POST)
    public Map<String, Object> login(UserVO userVO) {
        Map<String, Object> map = userService.login(userVO);
        return map;
    }*/

    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public RespBean logout(HttpServletRequest request) {
        RespBean respBean = userService.logout(request);
        return respBean;
    }

    @RequestMapping(value = "/user/getUserInfo", method = RequestMethod.GET)
    public Map<String, Object> getUserInfo(HttpServletRequest request) {
        Map<String, Object> userInfo = userService.getUserInfo(request);
        return userInfo;
    }

    @RequestMapping(value = "/user/updateUserInfo", method = RequestMethod.POST)
    public Map<String, Object> updateUserInfo(UserVO userVO) {
        Map<String, Object> map = userService.updateUserInfo(userVO);
        return map;
    }

}
