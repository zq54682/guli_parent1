package com.atguigu.eduservice.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@ApiModel(value = "course查询对象", description = "课程查询对象封装")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程名称,模糊查询")
    private String title;
    @ApiModelProperty(value = "课程状态， 已发布 未发布 两种")
    private String status;

}
