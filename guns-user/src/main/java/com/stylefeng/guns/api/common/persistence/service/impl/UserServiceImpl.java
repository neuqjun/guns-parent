package com.stylefeng.guns.api.common.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.api.user.vo.UserVO;
import com.stylefeng.guns.api.common.persistence.dao.MtimeUserTMapper;
import com.stylefeng.guns.api.common.persistence.model.MtimeUserT;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private MtimeUserTMapper mtimeUserTMapper;
    @Override
    public Boolean insert(UserVO userVO) {
        MtimeUserT mtimeUserT = new MtimeUserT();
        BeanUtils.copyProperties(userVO,mtimeUserT);
        Integer affectedRows = mtimeUserTMapper.insert(mtimeUserT);
        if (affectedRows > 0) {
            return true;
        }
        return false;
    }
}
