package com.stylefeng.guns.api.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.config.properties.JwtProperties;
import com.stylefeng.guns.api.user.bean.RespBean;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.api.user.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    public RespBean register(UserVO userVO) {
        RespBean respBean = userService.insert(userVO);
        return respBean;
    }

    @RequestMapping(value = "/user/check", method = RequestMethod.POST)
    public RespBean check(UserVO userVO) {
        RespBean respBean = userService.checkUsername(userVO);
        return respBean;
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public RespBean logout(HttpServletRequest request) {

        String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
        }
        RespBean respBean = userService.logout(authToken);
        return respBean;
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
