package com.atguigu.oss.service.impl;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.atguigu.oss.service.FileService;
import com.atguigu.oss.utils.ReadPropertiesOssConst;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServieceImpl implements FileService {

    @Override
    public String uploadFile(MultipartFile file) {

        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ReadPropertiesOssConst.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ReadPropertiesOssConst.KEY_ID;
        String accessKeySecret = ReadPropertiesOssConst.KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ReadPropertiesOssConst.BUCKET_NAME;


        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String uploadUrl = "";

        try {
            /**
             * 1. 判断 oss 实例是否存在，不存在的话就创建
             */
            if (!ossClient.doesBucketExist(bucketName)){
                // 创建 bucket
                ossClient.createBucket(bucketName);
                // 设置 oss 实例的访问权限为公共读
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }

            /**
             * 2. 获取上传文件的 文件流
             */
            InputStream inputStream = file.getInputStream();

            /**
             * 3. 构建唯一的文件路径
             */
            // 获取文件全部名称
            String originalname = file.getOriginalFilename();
            System.out.println(originalname);
            // 生成唯一的 uuid 字符串
            String uuidname = UUID.randomUUID().toString().replace("-", "");
            System.out.println(uuidname);
            // 把文件按照日期分类
            String datename = new DateTime().toString("yyyy/MM/dd");
            // 拼接
            String objectName = "" + datename + "/" + uuidname + "/" + originalname;

            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, inputStream);

            /**
             * 获取 url
             * 示例：https://atguigu-eduteacher-zq.oss-cn-shenzhen.aliyuncs.com/demmo.jfif
             */
            uploadUrl = "https://" + bucketName + "." + endpoint + "/" + objectName;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        return uploadUrl;

    }



}
