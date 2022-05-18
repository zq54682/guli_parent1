package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-04-04
 */
public interface EduCommentService extends IService<EduComment> {

    Map<String,Object> getAllComment(String courseId, long page, long limit);

}
