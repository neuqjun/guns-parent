package com.stylefeng.guns.api.user;
import com.stylefeng.guns.api.user.bean.RespBean;
import com.stylefeng.guns.api.user.vo.UserVO;
import java.util.Map;

public interface UserService {
    RespBean insert(UserVO userVO);

    RespBean checkUsername(UserVO userVO);

    String login(UserVO userVO);

    RespBean logout(String token);

    UserVO getUserInfo(String token);

    Map<String,Object> updateUserInfo(UserVO userVO);

    //Boolean selectByUsernameAndPassword(String username, String password);

    Boolean selectByUsername(String username);

    UserVO selectByUsernameAndPassword(String username,String password);

}
