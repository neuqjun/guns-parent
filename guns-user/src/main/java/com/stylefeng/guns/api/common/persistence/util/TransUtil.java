package com.stylefeng.guns.api.common.persistence.util;

import com.stylefeng.guns.api.common.persistence.model.MtimeUserT;
import com.stylefeng.guns.api.user.vo.UserVO;
import org.springframework.beans.BeanUtils;

public class TransUtil {
    public static MtimeUserT changeUserVOToMtimeUserT(UserVO userVO) {
        MtimeUserT mtimeUserT = new MtimeUserT();
        mtimeUserT.setUuid(userVO.getUuid());
        mtimeUserT.setUserName(userVO.getUsername());
        mtimeUserT.setUserPwd(userVO.getPassword());
        mtimeUserT.setNickName(userVO.getNickname());
        mtimeUserT.setUserSex(userVO.getSex());
        mtimeUserT.setBirthday(userVO.getBirthday());
        mtimeUserT.setEmail(userVO.getEmail());
        mtimeUserT.setUserPhone(userVO.getPhone());
        mtimeUserT.setAddress(userVO.getAddress());
        mtimeUserT.setHeadUrl(userVO.getHeadAddress());
        mtimeUserT.setBiography(userVO.getBiography());
        mtimeUserT.setLifeState(userVO.getLifeState());
        mtimeUserT.setBeginTime(userVO.getCreatTime());
        mtimeUserT.setUpdateTime(userVO.getUpdateTime());
        return mtimeUserT;
    }

    public static UserVO changeMtimeUserTToUserVO(MtimeUserT mtimeUserT) {
        UserVO userVO = new UserVO();
        userVO.setUuid(mtimeUserT.getUuid());
        userVO.setUsername(mtimeUserT.getUserName());
        userVO.setPassword(mtimeUserT.getUserPwd());
        userVO.setNickname(mtimeUserT.getNickName());
        userVO.setSex(mtimeUserT.getUserSex());
        userVO.setBirthday(mtimeUserT.getBirthday());
        userVO.setEmail(mtimeUserT.getEmail());
        userVO.setPhone(mtimeUserT.getUserPhone());
        userVO.setAddress(mtimeUserT.getAddress());
        userVO.setHeadAddress(mtimeUserT.getHeadUrl());
        userVO.setBiography(mtimeUserT.getBiography());
        userVO.setLifeState(mtimeUserT.getLifeState());
        userVO.setCreatTime(mtimeUserT.getBeginTime());
        userVO.setUpdateTime(userVO.getUpdateTime());
        return userVO;
    }
}
