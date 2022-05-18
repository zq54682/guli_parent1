package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/frontteacher")
@CrossOrigin
public class FrontTeacherContoller {

    @Autowired
    EduTeacherService eduTeacherService;
    @Autowired
    EduCourseService eduCourseService;

    /**
     * 前台分页查询讲师
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/getFrontTeacherPage/{page}/{limit}")
    public R getFrontTeacherPage(@PathVariable Long page, @PathVariable Long limit){
        Map<String, Object> map = eduTeacherService.getTeacherPage(page, limit);
        return R.ok().data(map);
    }

    /**
     * 前台根据讲师id 查询讲师
     */
    @GetMapping("/getTeacherById/{id}")
    public R getTeacherById(@PathVariable String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    /**
     * 前台根据讲师id 查询讲师讲的所有课程
     */
    @GetMapping("/getCourseList/{id}")
    public R getCourseList(@PathVariable String id){
        List<EduCourse> courses = eduCourseService.getCourseList(id);
        return R.ok().data("courses", courses);
    }

    /**
     * 前台根据讲师id 分页查询讲师讲的所有课程
     */
    @GetMapping("/getCoursePageListByTid/{id}/{page}/{limit}")
    public R getCoursePageListByTid(@PathVariable String id, @PathVariable Long page, @PathVariable Long limit){
        Map<String, Object> map = eduCourseService.getCoursePageListByTid(id, page, limit);
        return R.ok().data(map);
    }

}
