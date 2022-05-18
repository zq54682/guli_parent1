package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {

    /**
     * 用户登陆
     * @return
     */
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }


    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("info")
    public R info(){
        return R.ok().data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
