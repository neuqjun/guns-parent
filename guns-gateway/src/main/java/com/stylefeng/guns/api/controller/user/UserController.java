package com.stylefeng.guns.api.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.api.user.vo.UserVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(check = false)
    private UserService userService;

    @RequestMapping(value = "/insert")
    public String insertUser(UserVO userVO) {
        Boolean insert = userService.insert(userVO);
        if (insert) {
            return "插入成功！";
        }
        return "插入失败！";
    }
}
