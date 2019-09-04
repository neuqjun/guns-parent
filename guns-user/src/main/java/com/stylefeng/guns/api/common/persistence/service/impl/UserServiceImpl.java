package com.stylefeng.guns.api.common.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.common.persistence.util.TransUtil;
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
    private JwtTokenUtil jwtTokenUtil;


    @Override
    public RespBean insert(UserVO userVO) {
        RespBean respBean = new RespBean();
        try {
            MtimeUserT mtimeUserT = TransUtil.changeUserVOToMtimeUserT(userVO);
            Boolean aBoolean = selectByUsername(userVO.getUsername());
            if (aBoolean) {
                respBean.setStatus(1);
                respBean.setMsg("用户已存在");
                return respBean;
            } else {
                String encryptPassword = MD5Util.encrypt(mtimeUserT.getUserPwd());
                mtimeUserT.setUserPwd(encryptPassword);
                mtimeUserT.setBeginTime(new Date());
                mtimeUserT.setUpdateTime(new Date());
                Integer affectedRows = mtimeUserTMapper.insert(mtimeUserT);
                if (affectedRows == 1) {
                    respBean.setStatus(0);
                    respBean.setMsg("注册成功");
                    return respBean;
                } else {
                    respBean.setStatus(999);
                    respBean.setMsg("系统异常，请联系管理员");
                    return respBean;
                }
            }
        } catch (Exception e) {
            respBean.setStatus(999);
            respBean.setMsg("系统异常，请联系管理员");
            return respBean;
        }
    }

    @Override
    public RespBean checkUsername(String username) {
        RespBean respBean = new RespBean();
        MtimeUserT mtimeUserT = mtimeUserTMapper.selectByUsername(username);
        if (mtimeUserT == null) {
            respBean.setStatus(0);
            respBean.setMsg("验证成功");
            return respBean;
        } else if (mtimeUserT != null) {
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
    public String login(UserVO userVO) {
        String username = userVO.getUsername();
        String password = userVO.getPassword();
        String encrypt = MD5Util.encrypt(password);
        MtimeUserT mtimeUserT = mtimeUserTMapper.selectByUsernameAndPassword(username,encrypt);
        String userId = Integer.toString(mtimeUserT.getUuid());
        return userId;
    }



    @Override
    public RespBean logout(String token) {
        return null;
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

    /*@Override
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
    }*/

    @Override
    public Boolean selectByUsername(String username) {
        MtimeUserT mtimeUserT = mtimeUserTMapper.selectByUsername(username);
        if (mtimeUserT != null) {
            return true;
        }
        return false;
    }

    @Override
    public UserVO selectByUsernameAndPassword(String username, String password) {
        MtimeUserT mtimeUserT = mtimeUserTMapper.selectByUsernameAndPassword(username, password);
        UserVO userVO = TransUtil.changeMtimeUserTToUserVO(mtimeUserT);
        return userVO;
    }
}
