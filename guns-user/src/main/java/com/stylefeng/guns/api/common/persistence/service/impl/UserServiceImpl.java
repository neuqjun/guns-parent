package com.stylefeng.guns.api.common.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.common.persistence.util.TransUtil;
import com.stylefeng.guns.api.config.properties.JwtProperties;
import com.stylefeng.guns.api.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.api.user.bean.RespBean;
import com.stylefeng.guns.api.user.vo.UserVO;
import com.stylefeng.guns.api.common.persistence.dao.MtimeUserTMapper;
import com.stylefeng.guns.api.common.persistence.model.MtimeUserT;
import com.stylefeng.guns.core.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private MtimeUserTMapper mtimeUserTMapper;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public RespBean insert(UserVO userVO) {
        MtimeUserT mtimeUserT = TransUtil.changeUserVOToMtimeUserT(userVO);
        RespBean respBean = new RespBean();
        String encryptPassword = MD5Util.encrypt(mtimeUserT.getUserPwd());
        mtimeUserT.setUserPwd(encryptPassword);
        mtimeUserT.setBeginTime(new Date());
        mtimeUserT.setUpdateTime(new Date());
        Integer affectedRows = mtimeUserTMapper.insert(mtimeUserT);
        if (affectedRows == 1) {
            respBean.setStatus(0);
            respBean.setMsg("注册成功");
            return respBean;
        } else if (affectedRows != 0) {
            respBean.setStatus(1);
            respBean.setMsg("用户已存在");
            return respBean;
        } else {
            respBean.setStatus(999);
            respBean.setMsg("系统异常，请联系管理员");
            return respBean;
        }
    }

    @Override
    public RespBean checkUsername(UserVO userVO) {
        MtimeUserT mtimeUserT = TransUtil.changeUserVOToMtimeUserT(userVO);
        RespBean respBean = new RespBean();
        MtimeUserT mtimeUserT1 = mtimeUserTMapper.selectOne(mtimeUserT);
        if (mtimeUserT1 != null) {
            respBean.setStatus(0);
            respBean.setMsg("验证成功");
            return respBean;
        } else if (mtimeUserT1 == null) {
            respBean.setStatus(1);
            respBean.setMsg("用户已存在");
            return respBean;
        } else {
            respBean.setStatus(999);
            respBean.setMsg("系统异常，请联系管理员");
            return respBean;
        }
    }

    @Override
    public Map<String,Object> login(UserVO userVO) {
        MtimeUserT mtimeUserT = TransUtil.changeUserVOToMtimeUserT(userVO);
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();
        mtimeUserT.setUserPwd(MD5Util.encrypt(mtimeUserT.getUserPwd()));
        MtimeUserT mtimeUserT1 = mtimeUserTMapper.selectOne(mtimeUserT);
        if (mtimeUserT1 != null) {
            map.put("status", 0);
            data.put("randomKey", "xxxxxx");
            data.put("token", "这里是一长串字符");
            map.put("data", data);
            return map;
        } else if (mtimeUserT1 == null) {
            map.put("status", 1);
            map.put("msg", "用户名或密码错误");
            return map;
        } else {
            map.put("status", 999);
            map.put("msg", "系统异常，请联系管理员");
            return map;
        }
    }

    @Override
    public RespBean logout(String token) {
        //从 request中取出 Authorization这个请求头，进而获得token(之前在过滤器中已经鉴权了)
        //从token信息中取出用户信息等，并销毁token(比如设置成已过期)
        RespBean respBean = new RespBean();
        /*try {
            String requestHeader = request.getHeader(jwtProperties.getHeader());
            if (requestHeader == null) {
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
        }*/
        return respBean;
    }

    @Override
    public UserVO getUserInfo(String token) {
        // 从 token 中获取用户信息，根据用户信息再去数据库中查询完整的用户信息
        String username = jwtTokenUtil.getUsernameFromToken(token);
        MtimeUserT mtimeUserT = new MtimeUserT();
        mtimeUserT.setUserName(username);
        MtimeUserT mtimeUserT1 = mtimeUserTMapper.selectOne(mtimeUserT);
        UserVO userVO = TransUtil.changeMtimeUserTToUserVO(mtimeUserT1);
        return userVO;
    }

    @Override
    public Map<String, Object> updateUserInfo(UserVO userVO) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            MtimeUserT mtimeUserT = TransUtil.changeUserVOToMtimeUserT(userVO);
            mtimeUserT.setUpdateTime(new Date());
            Integer integer = mtimeUserTMapper.updateById(mtimeUserT);
            MtimeUserT mtimeUserT1 = mtimeUserTMapper.selectById(mtimeUserT.getUuid());
            UserVO userVO1 = TransUtil.changeMtimeUserTToUserVO(mtimeUserT1);
            if (integer == 1) {
                map.put("status", 0);
                map.put("data", userVO1);
                return map;
            } else {
                map.put("status", 1);
                map.put("msg", "用户信息修改失败");
                return map;
            }
        } catch (Exception e) {
            map.put("status", 999);
            map.put("msg", "系统异常，请联系管理人员");
            return map;
        }
    }

    @Override
    public Boolean selectByUsernameAndPassword(String username, String password) {
        MtimeUserT mtimeUserT = new MtimeUserT();
        mtimeUserT.setUserName(username);
        mtimeUserT.setUserPwd(MD5Util.encrypt(password));
        MtimeUserT mtimeUserT1 = mtimeUserTMapper.selectOne(mtimeUserT);
        if (mtimeUserT1 != null) {
            return true;
        } else {
            return false;
        }
    }
}
