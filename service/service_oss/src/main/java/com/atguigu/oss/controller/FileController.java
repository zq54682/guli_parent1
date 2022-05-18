package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.FileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * oss 文件上传
 */
@RestController
@RequestMapping("/ossservice/fileupload")
@CrossOrigin        // 解决简单的跨域问题
public class FileController {

    @Autowired
    FileService fileService;

    @ApiOperation(value = "文件上传")
    @PostMapping("/avatarupload")
    public R uploadFile(MultipartFile file){
        String url = fileService.uploadFile(file);
        return R.ok().data("avatarUrl", url);
    }

}
