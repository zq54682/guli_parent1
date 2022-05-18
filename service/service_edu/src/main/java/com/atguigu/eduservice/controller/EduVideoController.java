package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VideoClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-03-15
 */
@RestController
@RequestMapping("/eduservice/eduvideo")
@CrossOrigin
public class EduVideoController {

    @Autowired
    EduVideoService eduVideoService;
    @Autowired
    VideoClient videoClient;

    /**
     * 保存 video
     * @param eduVideo
     * @return
     */
    @PostMapping("/saveVideo")
    public R saveVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    /**
     * 修改 video
     * @param eduVideo
     * @return
     */
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        System.out.println(eduVideo);
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

    /**
     * 删除 video
     */
    @DeleteMapping("/deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        /**
         * 先删除小节的视频
         */
        EduVideo eduVideo = eduVideoService.getById(id);
        String videoId = eduVideo.getVideoSourceId();
        //如果id不为空就调用 video 模块删除
        Boolean success = false;
        if (!StringUtils.isEmpty(videoId)){
            R r = videoClient.deleteALYVideo(videoId);
            success = r.getSuccess();
        }
        /**
         * 再删除小节
         */
        if (success){
            eduVideoService.removeById(id);
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 查询 video
     */
    @GetMapping("/getVideoById/{id}")
    public R getVideoById(@PathVariable String id){
        EduVideo eduVideo = eduVideoService.getById(id);
        return R.ok().data("eduVideo", eduVideo);
    }

}

