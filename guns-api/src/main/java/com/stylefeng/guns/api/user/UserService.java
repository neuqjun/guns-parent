package com.stylefeng.guns.api.user;

import com.stylefeng.guns.api.user.bean.RespBean;
import com.stylefeng.guns.api.user.vo.UserVO;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService {
    RespBean insert(UserVO userVO);

    RespBean checkUsername(UserVO userVO);

    Map<String,Object> login(UserVO userVO);

    RespBean logout(String token);

    UserVO getUserInfo(String token);

    Map<String,Object> updateUserInfo(UserVO userVO);

    Boolean selectByUsernameAndPassword(String username, String password);

}
