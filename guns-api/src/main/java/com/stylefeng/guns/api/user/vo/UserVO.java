package com.stylefeng.guns.api.user.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: jia.xue
 * @create: 2019-08-29 16:02
 * @Description
 **/
@Data
public class UserVO implements Serializable {


    private static final long serialVersionUID = -9153485667299839167L;
    private String userName;
    /**
     * 用户密码
     */

    private String userPwd;
    /**
     * 用户昵称
     */

    private String nickName;
    /**
     * 用户性别 0-男，1-女
     */

    private Integer userSex;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 用户住址
     */
    private String address;
    /**
     * 头像URL
     */
    private String headUrl;
    /**
     * 个人介绍
     */
    private String biography;
    /**
     * 生活状态 0-单身，1-热恋中，2-已婚，3-为人父母
     */
    private Integer lifeState;
    /**
     * 创建时间
     */
    private Date beginTime;
    /**
     * 修改时间
     */
    private Date updateTime;


}