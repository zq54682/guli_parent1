package com.atguigu.video.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.video.service.VideoUploadService;
import com.atguigu.video.utils.AliyunVodSDKUtils;
import com.atguigu.video.utils.ConstantPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/videoservice/videoupload")
@CrossOrigin        // 解决简单的跨域问题
public class VideoController {

    @Autowired
    VideoUploadService videoUploadService;

    /**
     * 向阿里云上唇视频
     * @param file
     * @return
     */
    @PostMapping("/uploadVideoToAli")
    public R uploadVideoToAli(MultipartFile file){
        String videoId = videoUploadService.uploadVideo(file);
        System.out.println("videoID : " + videoId);
        return R.ok().data("videoId", videoId);
    }

    /**
     * 从阿里云中删除视频
     * @param videoId
     * @return
     */
    @DeleteMapping("/deleteALYVideo/{videoId}")
    public R deleteALYVideo(@PathVariable("videoId") String videoId){
        videoUploadService.deleteVideoFromALY(videoId);
        return R.ok();
    }


    /**
     * 从阿里云中删除多个视频
     */
    @DeleteMapping("deleteAllALYVideo")
    public R deleteAllALYVideo(@RequestParam("moreVideoId") String moreVideoId){
        videoUploadService.deleteAllVideoFromALY(moreVideoId);
        return R.ok();
    }


    //根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient client =
                    AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        }catch(Exception e) {
            System.out.println(e);
            return R.error();
        }
    }

}
