package com.atguigu.commonutils.order_vo;

import lombok.Data;
import java.util.Date;

/**
 * <p>
 * 讲师
 * </p>
 *
 * @author atguigu
 * @since 2022-03-03
 */
@Data
public class TeacherOrder {

    private String id;

    private String name;

    private String intro;

    private String career;

    private Integer level;

    private String avatar;

    private Integer sort;

    private Boolean isDeleted;

    private Date gmtCreate;

    private Date gmtModified;


}
