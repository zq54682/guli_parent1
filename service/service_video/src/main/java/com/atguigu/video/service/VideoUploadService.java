package com.atguigu.video.service;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoUploadService {
    String uploadVideo(MultipartFile file);

    void deleteVideoFromALY(String videoId);

    void deleteAllVideoFromALY(String moreVideoId);
}
