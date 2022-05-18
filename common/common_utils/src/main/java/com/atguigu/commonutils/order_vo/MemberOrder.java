package com.atguigu.commonutils.order_vo;


import lombok.Data;
import java.util.Date;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author atguigu
 * @since 2022-03-29
 */
@Data
public class MemberOrder {

    private String id;

    private String openid;

    private String mobile;

    private String password;

    private String nickname;

    private Integer sex;

    private Integer age;

    private String avatar;

    private String sign;

    private Boolean isDisabled;

    private Boolean isDeleted;

    private Date gmtCreate;

    private Date gmtModified;


}
