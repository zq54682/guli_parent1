package com.atguigu.eduservice.client;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.impl.UcenterClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * nacos
 * 远程服务调用接口
 */
@Component
@FeignClient(name = "service-ucenter", fallback = UcenterClientImpl.class)
public interface UcenterClient {

    /**
     * @FeignClient 注解用于指定从哪个服务中调用功能 ，名称与被调用的服务名保持一致。
     * @DeleteMapping 必须使用全路径，注解用于对被调用的微服务进行地址映射。
     * @PathVariable 注解一定要指定参数名称，否则出错
     * @Component 注解防止，在其他位置注入CodClient时idea报错
     */

    /**
     * 从ucenter模块查询 用户 信息
     */
    @GetMapping("/ucenterservice/member/getMemberById/{id}")
    public R getMemberById(@PathVariable("id") String id);



}
