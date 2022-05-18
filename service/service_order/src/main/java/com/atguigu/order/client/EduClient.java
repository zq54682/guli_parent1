package com.atguigu.order.client;

import com.atguigu.commonutils.order_vo.CourseOrder;
import com.atguigu.commonutils.order_vo.TeacherOrder;
import com.atguigu.order.client.impl.EduClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-edu", fallback = EduClientImpl.class)
public interface EduClient {

    /**
     * @FeignClient 注解用于指定从哪个服务中调用功能 ，名称与被调用的服务名保持一致。
     * @DeleteMapping 必须使用全路径，注解用于对被调用的微服务进行地址映射。
     * @PathVariable 注解一定要指定参数名称，否则出错
     * @Component 注解防止，在其他位置注入CodClient时idea报错
     */

    /**
     * 从edu模块查询 课程 信息
     */
    @GetMapping("/eduservice/eduCourse/getCourseByIdOrder/{courseId}")
    public CourseOrder getCourseByIdOrder(@PathVariable("courseId") String courseId);

    /**
     * 从edu模块查询 讲师 信息
     */
    @GetMapping("/eduservice/teacher/selectByIdOrder/{id}")
    public TeacherOrder selectByIdOrder(@PathVariable("id") String id);

}
