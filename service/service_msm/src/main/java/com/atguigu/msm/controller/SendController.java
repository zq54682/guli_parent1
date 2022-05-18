package com.atguigu.msm.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msm.service.Msmservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/msmservice/msmduanxin")
@CrossOrigin
public class SendController {

    @Autowired
    Msmservice msmservice;

    /**
     * 注册时，发送短信的方法
     * @param phone
     * @return
     */
    @PostMapping("/send/{phone}")
    public R send(@PathVariable String phone){
        boolean status = msmservice.send(phone);
        if (status){
            System.out.println(status);
            return R.ok();
        }else {
            return R.error();
        }
    }

}
