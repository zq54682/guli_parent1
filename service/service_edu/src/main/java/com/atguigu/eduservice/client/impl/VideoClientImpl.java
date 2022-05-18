package com.atguigu.eduservice.client.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VideoClient;
import org.springframework.stereotype.Component;

@Component
public class VideoClientImpl implements VideoClient {


    @Override
    public R deleteALYVideo(String videoId) {
        return R.error().message("删除视频失败！");
    }


    @Override
    public R deleteAllALYVideo(String moreVideoId) {
        return R.error().message("删除视频失败！");
    }
}
