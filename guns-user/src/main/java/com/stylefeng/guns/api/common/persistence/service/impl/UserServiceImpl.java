package com.stylefeng.guns.api.common.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.common.persistence.util.TransUtil;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.api.user.bean.RespBean;
import com.stylefeng.guns.api.user.vo.UserVO;
import com.stylefeng.guns.api.common.persistence.dao.MtimeUserTMapper;
import com.stylefeng.guns.api.common.persistence.model.MtimeUserT;
import com.stylefeng.guns.core.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private MtimeUserTMapper mtimeUserTMapper;

    @Override
    public RespBean insert(UserVO userVO) {
        MtimeUserT mtimeUserT = TransUtil.changeUserVOToMtimeUserT(userVO);
        RespBean respBean = new RespBean();
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
        //userVO.setUserPwd(MD5Util.encrypt(userVO.getUserPwd()));
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
    public RespBean logout(HttpServletRequest request) {
        //从 request中取出 Authorization这个请求头，进而获得token
        // 要先鉴权之后再退出,即验证 token信息，从token信息中取出用户信息等，并销毁token(比如设置成已过期)
        RespBean respBean = new RespBean();
        int i = 0;
        if (i == 0) {
            respBean.setStatus(0);
            respBean.setMsg("成功退出");
            return respBean;
        } else if (i == 1) {
            respBean.setStatus(1);
            respBean.setMsg("退出失败，用户尚未登录");
            return respBean;
        } else {
            respBean.setStatus(999);
            respBean.setMsg("系统异常，请联系管理员");
            return respBean;
        }
    }

    @Override
    public Map<String, Object> getUserInfo(HttpServletRequest request) {
        // 从 request 中取出 token 从中获取用户信息，根据用户信息再去数据库中查询完整的用户信息
        Map<String, Object> map = new HashMap<>();

        MtimeUserT mtimeUserT = new MtimeUserT();
        mtimeUserT.setUserName("xxx");
        mtimeUserT.setUserPwd("xxxx");
        MtimeUserT mtimeUserT1 = mtimeUserTMapper.selectOne(mtimeUserT);
        UserVO userVO = TransUtil.changeMtimeUserTToUserVO(mtimeUserT1);
        if (mtimeUserT1 != null) {
            map.put("status", 0);
            map.put("data", userVO);
            return map;
        } else if (mtimeUserT1 == null) {
            map.put("status", 1);
            map.put("msg", "查询失败，用户尚未登录");
            return map;
        } else {
            map.put("status", 999);
            map.put("msg", "系统异常，请联系管理人员");
            return map;
        }
    }

    @Override
    public Map<String, Object> updateUserInfo(UserVO userVO) {
        MtimeUserT mtimeUserT = TransUtil.changeUserVOToMtimeUserT(userVO);
        HashMap<String, Object> map = new HashMap<>();
        mtimeUserT.setUpdateTime(new Date());
        Integer integer = mtimeUserTMapper.updateById(mtimeUserT);
        UserVO userVO1 = TransUtil.changeMtimeUserTToUserVO(mtimeUserT);
        if (integer == 1) {
            map.put("status", 0);
            map.put("data", userVO1);
            return map;
        } else if (integer == 0) {
            map.put("status", 1);
            map.put("msg", "用户信息修改失败");
            return map;
        } else {
            map.put("status", 999);
            map.put("msg", "系统异常，请联系管理人员");
            return map;
        }
    }
}
