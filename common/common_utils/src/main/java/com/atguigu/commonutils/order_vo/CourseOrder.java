package com.atguigu.commonutils.order_vo;


import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 课程
 * </p>
 *
 * @author atguigu
 * @since 2022-03-15
 */
@Data
public class CourseOrder {

    private String id;

    private String teacherId;

    private String subjectId;

    private String subjectParentId;

    private String title;

    private BigDecimal price;

    private Integer lessonNum;

    private String cover;

    private Long buyCount;

    private Long viewCount;

    private Long version;

    private String status;

    private Integer isDeleted = 0;

    private Date gmtCreate;

    private Date gmtModified;


}
