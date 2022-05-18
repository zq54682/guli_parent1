package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.impl.VideoClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * nacos
 * 远程服务调用接口
 */
@Component
@FeignClient(name = "service-vod", fallback = VideoClientImpl.class)
public interface VideoClient {

    /**
     * @FeignClient 注解用于指定从哪个服务中调用功能 ，名称与被调用的服务名保持一致。
     * @DeleteMapping 必须使用全路径，注解用于对被调用的微服务进行地址映射。
     * @PathVariable 注解一定要指定参数名称，否则出错
     * @Component 注解防止在其他位置注入CodClient时idea报错
     */

    /**
     * 从阿里云中删除视频
     * @param videoId
     * @return
     */
    @DeleteMapping("/videoservice/videoupload/deleteALYVideo/{videoId}")
    public R deleteALYVideo(@PathVariable("videoId") String videoId);


    /**
     * 从阿里云中删除多个视频
     */
    @DeleteMapping("/videoservice/videoupload/deleteAllALYVideo")
    public R deleteAllALYVideo(@RequestParam("moreVideoId") String moreVideoId);

}
