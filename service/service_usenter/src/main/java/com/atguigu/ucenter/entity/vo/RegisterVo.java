package com.atguigu.ucenter.entity.vo;

import lombok.Data;

/**
 * 注册封装对象
 */
@Data
public class RegisterVo {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 移动电话号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

}
