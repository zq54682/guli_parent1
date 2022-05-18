package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.commonutils.order_vo.CourseOrder;
import com.atguigu.eduservice.entity.CourseRelease;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.info.CourseInfo;
import com.atguigu.eduservice.entity.query.CourseQuery;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-03-15
 */
@RestController
@RequestMapping("/eduservice/eduCourse")
@CrossOrigin
public class EduCourseController {

    @Autowired
    EduCourseService eduCourseService;
    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    EduChapterService eduChapterService;
    @Autowired
    EduVideoService eduVideoService;

    /**
     * 保存 课程
     * @param courseInfo
     * @return
     */
    @PostMapping("/saveCourse")
    public R saveCourse(@RequestBody CourseInfo courseInfo){
        String courseId = eduCourseService.saveCourseAndDescriptrion(courseInfo);
        System.out.println(courseId);
        return R.ok().data("courseId", courseId);
    }

    /**
     * 修改方法
     * @param courseInfo
     * @return
     */
    @PostMapping("/updateCourse")
    public R updateCourse(@RequestBody CourseInfo courseInfo){
        eduCourseService.updateCourseAndDescription(courseInfo);
        return R.ok();
    }

    /**
     * 获取 courseinfo， 用于回显
     * @param id
     * @return
     */
    @GetMapping("/getCourseInfo/{id}")
    public R getCourseInfo(@PathVariable String id){
        CourseInfo courseInfo = eduCourseService.getCourseInfo(id);
        return R.ok().data("courseInfo", courseInfo);
    }

    /**
     * 获取最终发布的页面信息对象
     * @param id
     * @return
     */
    @GetMapping("/getCourseRelease/{id}")
    public R getCourseRelease(@PathVariable String id){
        CourseRelease courseReleases = eduCourseService.getCourseReleases(id);
        return R.ok().data("courseReleases", courseReleases);
    }

    /**
     * 发布课程，就是修改课程的 status
     * Draft 默认值，未发布，前台用户无法看到
     * Normal 已发布，前台用户可以看到
     */
    @PostMapping("/updateCourseStadu/{id}")
    public R updateCourseStadu(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    /**
     * 查询所有课程列表
     */
    @GetMapping("/getAllCourse")
    public R getAllCourse(){
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("alllist", list);
    }


    /**
     * 分页查询课程
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/pageCourse/{page}/{limit}")
    public R pageCourse(@PathVariable long page,
                         @PathVariable long limit){
        /**
         * 创建分页对象, 参数使用请求中传递的
         */
        Page<EduCourse> pageT = new Page<>(page, limit);
        /**
         * 执行分页过程
         */
        eduCourseService.page(pageT, null);
        /**
         * 当前页的数据集合
         */
        List<EduCourse> courseList = pageT.getRecords();
        /**
         * 总数据
         */
        long total = pageT.getTotal();
        /**
         * 创建个Map，用于传递
         * 或者使用下面的方法：
         *      return R.ok().data("total", total).data("data", records);
         */
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("data", courseList);
        return R.ok().data(map);
    }


    /**
     * 多条件联合分页查询课程
     */
    @PostMapping("pageQureyCourse/{page}/{limit}")
    public R pageQurey(@PathVariable long page,
                       @PathVariable long limit,
                       @RequestBody(required = false) CourseQuery courseQuery){
        /**
         * 1. 创建分页对象, 参数使用请求中传递的
         */
        Page<EduCourse> pageT = new Page<>(page, limit);
        /**
         * 2. 构建查询条件
         */
        // queryWrapper 是 mybatis plus 中实现查询的对象封装操作类
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        // 根据指定表字段排序
        queryWrapper.orderByAsc("title");
        // 如果条件不为空，需要组合条件
        System.out.println("courseQuery 不为null");
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        // 如果条件中的 name 值，不为null或者不为空，那么表中name字段按照name值来模糊查询
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(title)) {
            //构建条件
            queryWrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)) {
            queryWrapper.eq("status",status);
        }
        /**
         * 排序
         */
        queryWrapper.orderByDesc("gmt_create");
        /**
         * 3. 执行分页过程,带上查询条件
         */
        eduCourseService.page(pageT, queryWrapper);
        /**
         * 获取分页后的信息
         */
        long total = pageT.getTotal();
        List<EduCourse> list = pageT.getRecords();
        return R.ok().data("total",total).data("list",list);
    }


    @PostMapping("/removeCourseById/{id}")
    public R removeCourseDataById(@PathVariable String id){
        /**
         * 0. 优先删除视频
         */
        /**
         * 1. 删除小节
         */
        eduVideoService.removeVideoInCourse(id);
        /**
         * 2. 删除章节
         */
        eduChapterService.removeChapterInCourse(id);
        /**
         * 3. 删除课程简介
         */
        eduCourseDescriptionService.removeById(id);
        /**
         * 4. 删除课程
         */
        eduCourseService.removeById(id);
        return R.ok();
    }

    // 根据课程id获取课程信息
    @GetMapping("/getCourseById/{courseId}")
    public R getCourseById(@PathVariable("courseId") String courseId){
        EduCourse eduCourse = eduCourseService.getById(courseId);
        return R.ok().data("eduCourse", eduCourse);
    }

    // 根据课程id获取课程信息
    @GetMapping("/getCourseByIdOrder/{courseId}")
    public CourseOrder getCourseByIdOrder(@PathVariable("courseId") String courseId){
        EduCourse eduCourse = eduCourseService.getById(courseId);
        CourseOrder courseOrder = new CourseOrder();
        BeanUtils.copyProperties(eduCourse, courseOrder);
        return courseOrder;
    }


}

