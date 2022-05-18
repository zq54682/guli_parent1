package com.atguigu.ucenter.controller;


import com.atguigu.commonutils.JWTUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.order_vo.MemberOrder;
import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.entity.vo.LoginVo;
import com.atguigu.ucenter.entity.vo.RegisterVo;
import com.atguigu.ucenter.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-03-29
 */
@RestController
@RequestMapping("/ucenterservice/member")
@CrossOrigin
public class MemberController {

    @Autowired
    MemberService memberService;

    /**
     * 注册
     * @param registerVo
     * @return
     */
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo){
        boolean status = memberService.register(registerVo);
        if (status){
            return R.ok();
        }else {
            return R.error();
        }
    }


    /**
     * 登陆
     */
    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo){
        String token = memberService.login(loginVo);
        if (token != null){
            return R.ok().data("token", token);
        }else {
            return R.error();
        }
    }


    /**
     * 根据token获取用户登陆信息
     */
    @GetMapping("/getLoginInfo")
    public R getLoginInfo(HttpServletRequest request){
        try {
            String memberId = JWTUtils.getMemberIdByJwtToken(request);
            Member member = memberService.getById(memberId);
            return R.ok().data("loginVo", member);
        }catch (Exception e){
            System.out.println(e);
            return R.error();
        }
    }

    /**
     * 根据 id 查询用户
     */
    @GetMapping("/getMemberById/{id}")
    public MemberOrder getMemberById(@PathVariable("id") String id){
        Member member = memberService.getById(id);
        MemberOrder memberOrder = new MemberOrder();
        BeanUtils.copyProperties(member,memberOrder);
        return memberOrder;
    }

    @PostMapping("/getMemberByMyToken")
    public R getMemberByMyToken(HttpServletRequest request){
        String memberId = JWTUtils.getMemberIdByJwtToken(request);
        Member member = memberService.getById(memberId);
        return R.ok().data("member",member);
    }

    /**
     * 统计某一天的注册人数
     */
    @GetMapping("/zhuceCount/{day}")
    public R zhuceCount(@PathVariable("day") String day){
        Integer count = memberService.zhuceCountByDay(day);
        return R.ok().data("zhuceCount", count);
    }

}

