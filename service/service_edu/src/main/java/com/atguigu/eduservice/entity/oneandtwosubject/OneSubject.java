package com.atguigu.eduservice.entity.oneandtwosubject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程一级分类
 */
@Data
public class OneSubject {

    private String id;

    private String title;

    private List<TwoSubject> children = new ArrayList<>();


}
