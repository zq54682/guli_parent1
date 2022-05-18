package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapternadvideo.OneChapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-03-15
 */
public interface EduChapterService extends IService<EduChapter> {

    List<OneChapter> getChapterVideo(String courseId);

    void removeChapterInCourse(String id);


}
