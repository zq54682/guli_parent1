package com.atguigu.eduservice.controller.front;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.chapternadvideo.OneChapter;
import com.atguigu.eduservice.entity.info.CourseFrontInfo;
import com.atguigu.eduservice.entity.query.CourseFrontQuery;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/eduservice/frontcourse")
@CrossOrigin
public class FrontCourseController {

    @Autowired
    EduCourseService eduCourseService;
    @Autowired
    EduChapterService eduChapterService;

    /**
     * 多条件分页查询
     * @return
     */
    @PostMapping("/getCourseListByMore/{page}/{limit}")
    public R getCourseListByMore(@PathVariable Long page,
                                 @PathVariable Long limit,
                                 @RequestBody(required = false) CourseFrontQuery query){
        HashMap<String,Object> map = eduCourseService.getCourseListByMore(page, limit, query);
        return R.ok().data(map);
    }

    /**
     * 查询所有一级分类
     */
    @PostMapping("/getFrontAllSubject")
    public R getFrontAllSubject(){
        return R.ok();
    }

    /**
     * 前台系统课程详情页面方法
     * 根据课程id，获取课程详情页面所有信息
     */
    @GetMapping("/getCourseFrontInfo/{courseId}")
    public R getCourseFrontInfo(@PathVariable String courseId){
        CourseFrontInfo courseFrontInfo = eduCourseService.getCourseFrontInfoByCId(courseId);
        return R.ok().data("courseFrontInfo", courseFrontInfo);
    }

    /**
     * 根据课程id，获取所有的章节和小节
     */
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<OneChapter> chapterVideoList = eduChapterService.getChapterVideo(courseId);
        return R.ok().data("chapterVideoList", chapterVideoList);
    }

}