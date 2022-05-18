package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.mapper.EduCommentMapper;
import com.atguigu.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.lettuce.core.Limit;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-04-04
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {


    /**
     * 根据课程id，分页查询所有评论
     * @param courseId
     * @return
     */
    @Override
    public Map<String, Object> getAllComment(String courseId, long page, long Limit) {
        Page<EduComment> pageC = new Page<>(page, Limit);
        QueryWrapper<EduComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.orderByDesc("gmt_create");
        IPage<EduComment> commentIPage = baseMapper.selectPage(pageC, queryWrapper);
        List<EduComment> list = commentIPage.getRecords();
        long total = commentIPage.getTotal();
        Map<String,Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return map;
    }




}
