package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.JWTUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.UcenterClient;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-04-04
 */
@RestController
@RequestMapping("/eduservice/comment")
@CrossOrigin
public class EduCommentController {

    @Autowired
    EduCommentService eduCommentService;
    @Autowired
    UcenterClient ucenterClient;


    /**
     * 根据课程id，分页查询所有评论
     * @param courseId
     * @return
     */
    @GetMapping("/getAllCommentByCId/{courseId}/{page}/{limit}")
    public R getAllCommentByCId(@PathVariable("courseId") String courseId,
                                @PathVariable("page") Long page,
                                @PathVariable("limit") Long limit){
        Map<String,Object> map = eduCommentService.getAllComment(courseId, page, limit);
        return R.ok().data(map);
    }

    /**
     * 根据token，查询用户
     */
    @GetMapping("/getMemberByToken/{token}")
    public R getMemberByToken(@PathVariable String token){
        String memberId = JWTUtils.getMemberIdByToken(token);
        return R.ok().data("memberId", memberId);
    }

    /**
     * 调用远程模块 UCenter，根据用户id查询用户对象
     */
    @GetMapping("/getMemberByMid/{id}")
    public R getMemberByMid(@PathVariable String id){
        R r = ucenterClient.getMemberById(id);
        Object member = r.getData().get("member");
        return R.ok().data("member", member);
    }

    /**
     * 新增评论
     */
    @PostMapping("/saveComment")
    public R saveComment(@RequestBody EduComment eduComment){
        eduCommentService.save(eduComment);
        return R.ok();
    }

}

