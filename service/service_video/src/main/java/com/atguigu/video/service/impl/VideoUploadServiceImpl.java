package com.atguigu.video.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.atguigu.video.service.VideoUploadService;
import com.atguigu.video.utils.ConstantPropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.zip.CheckedOutputStream;

import static com.atguigu.video.utils.AliyunVodSDKUtils.initVodClient;

@Service
public class VideoUploadServiceImpl implements VideoUploadService {

    /**
     * 视频上传方法
     * @param file
     * @return
     */
    @Override
    public String uploadVideo(MultipartFile file) {
        String videoId = "";
        try {
            // 文件流
            InputStream inputStream = file.getInputStream();
            // 获取原始文件名
            String originalFilename = file.getOriginalFilename();
            // 上传后视频的标题
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET, title, originalFilename, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            videoId = response.getVideoId();
            if (!response.isSuccess()) {
                System.out.println("阿里云上传错误： " + "code： " + response.getCode() + ", message： " + response.getMessage());
            }
            return videoId;
        } catch (Exception e) {
            System.out.println(e);
        }finally {
            return videoId;
        }
    }

    @Override
    public void deleteVideoFromALY(String videoId) {
        try {
            /**
             * 1. 初始化对象
             */
            DefaultAcsClient client = initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            /**
             * 2. 创建删除视频request对象
             */
            DeleteVideoRequest request = new DeleteVideoRequest();
            /**
             * 3. 向 request 设置视频 id
             */
            request.setVideoIds(videoId);
            /**
             * 4. 调用初始化对象的方法实现删除
             */
             client.getAcsResponse(request);
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    @Override
    public void deleteAllVideoFromALY(String moreVideoId) {
        try {
            /**
             * 1. 初始化对象
             */
            DefaultAcsClient client = initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            /**
             * 2. 创建删除视频request对象
             */
            DeleteVideoRequest request = new DeleteVideoRequest();
            /**
             * 3. 向 request 设置视频 id
             */
            request.setVideoIds(moreVideoId);
            /**
             * 4. 调用初始化对象的方法实现删除
             */
            client.getAcsResponse(request);
        } catch (Exception e) {
            System.out.print(e);
        }
    }

}
