package com.atguigu.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 这是一个常量类，读取配置文件中有关 oss 的相关固定数据，并提供外部获取的方法
 * 实现 InitializingBean 接口的好处是
 *      当类加载完成后，已经完成配置文件的读取，然后会执行 afterPropertiesSet() 方法
 */
@Component
public class ReadPropertiesOssConst implements InitializingBean {

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyid;

    @Value("${aliyun.oss.file.keysecret}")
    private String keysecret;

    public static String BUCKET_NAME;
    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        BUCKET_NAME = bucketname;
        END_POINT = endpoint;
        KEY_ID = keyid;
        KEY_SECRET = keysecret;
    }
}
