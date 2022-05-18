package com.atguigu.statistics.client;

import com.atguigu.commonutils.R;
import com.atguigu.statistics.client.impl.UcenterClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
     * 统计某一天的注册人数
     */
    @GetMapping("/ucenterservice/member/zhuceCount/{day}")
    public R zhuceCount(@PathVariable("day") String day);


}
