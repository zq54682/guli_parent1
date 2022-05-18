package com.atguigu.order.client.impl;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.order_vo.CourseOrder;
import com.atguigu.commonutils.order_vo.TeacherOrder;
import com.atguigu.order.client.EduClient;
import org.springframework.stereotype.Component;

@Component
public class EduClientImpl implements EduClient {


    @Override
    public CourseOrder getCourseByIdOrder(String courseId) {
        return null;
    }

    @Override
    public TeacherOrder selectByIdOrder(String id) {
        return null;
    }
}
