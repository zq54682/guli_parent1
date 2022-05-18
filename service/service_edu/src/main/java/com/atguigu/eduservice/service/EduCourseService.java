package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.CourseRelease;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.info.CourseFrontInfo;
import com.atguigu.eduservice.entity.info.CourseInfo;
import com.atguigu.eduservice.entity.query.CourseFrontQuery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-03-15
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseAndDescriptrion(CourseInfo courseInfo);

    void updateCourseAndDescription(CourseInfo courseInfo);

    CourseInfo getCourseInfo(String id);

    CourseRelease getCourseReleases(String id);

    List<EduCourse> getShouyeCourse();

    List<EduCourse> getCourseList(String id);

    Map<String, Object> getCoursePageListByTid(String id, long page, long limit);

    HashMap<String, Object> getCourseListByMore(long page, long limit, CourseFrontQuery query);

    CourseFrontInfo getCourseFrontInfoByCId(String courseId);
}
