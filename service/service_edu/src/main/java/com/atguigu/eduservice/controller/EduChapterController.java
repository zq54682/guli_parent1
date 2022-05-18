package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapternadvideo.OneChapter;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
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
@RequestMapping("/eduservice/educhapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    EduChapterService eduChapterService;
    @Autowired
    EduVideoService eduVideoService;

    /**
     * 获取所有 大章节 和 小章节
     * @return
     */
    @GetMapping("/getChapterVideo/{id}")
    public R getChapterVideo(@PathVariable("id") String courseId){
        List<OneChapter> chapterVideoList = eduChapterService.getChapterVideo(courseId);
        System.out.println(chapterVideoList);
        return R.ok().data("chapterVideoList", chapterVideoList);
    }

    /**
     * 保存 chapter
     * @param eduChapter
     * @return
     */
    @PostMapping("/saveChapter")
    public R saveChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    /**
     * 修改 chapter
     * @param eduChapter
     * @return
     */
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        System.out.println("-------------------------------" + eduChapter.getId());
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    /**
     * 根据 id 获取 chapter
     * @param id
     * @return
     */
    @GetMapping("/getchapterById/{id}")
    public R getchapterById(@PathVariable String id){
        EduChapter chapter = eduChapterService.getById(id);
        return R.ok().data("chapter", chapter);
    }

    /**
     * 删除 chapter， 如果其下有小节，不予删除
     * @return
     */
    @DeleteMapping("/removeChapter/{id}")
    public R removeChapter(@PathVariable String id){
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("chapter_id", id);
        int count = eduVideoService.count(queryWrapper);
        if (count>0){
            return R.error();
        }else {
            eduChapterService.removeById(id);
            return R.ok();
        }
    }


}

