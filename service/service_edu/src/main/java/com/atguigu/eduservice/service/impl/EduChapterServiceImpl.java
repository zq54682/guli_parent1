package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapternadvideo.OneChapter;
import com.atguigu.eduservice.entity.chapternadvideo.TwoVideo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-03-15
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService eduVideoService;

    @Override
    public List<OneChapter> getChapterVideo(String courseId) {
        /**
         * 1. 获取当前课程的所有大章节
         */
        QueryWrapper<EduChapter> queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(queryWrapper1);
        /**
         * 2. 获取所有当前课程的小章节
         */
        QueryWrapper<EduVideo> queryWrapper2 = new QueryWrapper();
        queryWrapper2.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(queryWrapper2);
        /**
         * 3. 创建 大章节目录和小章节目录的 集合
         */
        List<OneChapter> onelist = new ArrayList();
        /**
         * 4. 遍历所有大章节，将章节名和id 赋值到章节目录对象中
         */
        for (EduChapter educhapter : eduChapterList) {
            OneChapter oneChapter = new OneChapter();
            BeanUtils.copyProperties(educhapter, oneChapter);
            List<TwoVideo> twolist = new ArrayList();
            for (EduVideo eduVideo : eduVideoList) {
                if (educhapter.getId().equals(eduVideo.getChapterId())){
                    TwoVideo twoVideo = new TwoVideo();
                    BeanUtils.copyProperties(eduVideo, twoVideo);
                    twolist.add(twoVideo);
                }
            }
            oneChapter.setChildren(twolist);
            onelist.add(oneChapter);
        }
        return onelist;
    }

    @Override
    public void removeChapterInCourse(String id) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        baseMapper.delete(queryWrapper);
    }
}
