package com.atguigu.ucenter.entity.vo;


import lombok.Data;

/**
 * 登陆封装对象
 */
@Data
public class LoginVo {

    /**
     * 移动电话号，作为账户登陆
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

}
