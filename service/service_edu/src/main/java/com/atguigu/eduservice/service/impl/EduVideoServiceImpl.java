package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VideoClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-03-15
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    VideoClient videoClient;


    /**
     * TODO: 删除小节的时候同时删除视频数据，后面完善
     * @param id
     */
    @Override
    public void removeVideoInCourse(String id) {
        /**
         * 1. 优先删除每个小节中的视频
         */
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        queryWrapper.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(queryWrapper);
        List<String> videoIdList = new ArrayList<>();
        for (EduVideo eduVideo : eduVideoList) {
            String videoSourceId = eduVideo.getVideoSourceId();
            if (!org.springframework.util.StringUtils.isEmpty(videoSourceId)){
                videoIdList.add(videoSourceId);
            }
        }
        if (videoIdList.size()>0){
            String videoIdJoin = StringUtils.join(videoIdList.toArray(), ",");
            videoClient.deleteAllALYVideo(videoIdJoin);
        }

        /**
         * 2. 再删除表中的小节
         */
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
