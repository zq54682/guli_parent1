package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.CourseRelease;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.info.CourseFrontInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2022-03-15
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CourseRelease getCourseRelease(String courseId);

    public CourseFrontInfo getCourseFrontInfo(String courseId);

}
