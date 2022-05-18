package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/crmbanner")
@CrossOrigin
public class CrmBannerController {

    @Autowired
    EduTeacherService eduTeacherService;

    @Autowired
    EduCourseService eduCourseService;


    /**
     * 获取前台页面首页的数据
     * 1. 热度最高的 8 个课程
     * 2. 热度最高的 4 名讲师
     * @return
     */
    @GetMapping("/front/getshouye")
    public R getshouye(){
        /**
         * 查询热门课程
         */
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("view_count");
        courseQueryWrapper.last("limit 8");
        List<EduCourse> courseList = eduCourseService.list(courseQueryWrapper);
        /**
         * 查询热门讲师
         */
        QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("sort");
        teacherQueryWrapper.last("limit 4");
        List<EduTeacher> teacherList = eduTeacherService.list(teacherQueryWrapper);

        return R.ok().data("courseList", courseList).data("teacherList", teacherList);
    }


    /**
     * 查询热度最高的 8 个课程
     * 分开写为了存放 redis
     */
    @GetMapping("/front/getshouyecourse")
    public R getShouyeCourse(){
        List<EduCourse> courseList = eduCourseService.getShouyeCourse();
        return R.ok().data("courseList", courseList);
    }

    /**
     * 查询热度最高的 4 名讲师
     * 分开写为了存放 redis
     */
    @GetMapping("/front/getshouyeteacher")
    public R getShouyeTeacher(){
        List<EduTeacher> teacherList = eduTeacherService.getShouyeTeacher();
        return R.ok().data("teacherList", teacherList);
    }

}
