package com.atguigu.order.controller;


import com.atguigu.commonutils.R;
import com.atguigu.order.service.TPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-04-06
 */
@RestController
@RequestMapping("/orderservice/pay")
@CrossOrigin
public class TPayLogController {

    @Autowired
    TPayLogService payLogService;

    /**
     * 生成二维码
     * @return
     */
    @GetMapping("/creatPayCode/{orderNo}")
    public R creatPayCode(@PathVariable String orderNo){
        Map map = payLogService.creatPayCode(orderNo);
        return R.ok().data(map);
    }

    /**
     * 获取支付状态的接口
     */
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null){
            return R.error().message("支付出错");
        }else if (map.get("trade_state").equals("SUCCESS")){
            // 支付成功的话，修改顶撞的状态，并向支付表添加支付记录
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }else {
            return R.ok().code(25000).message("支付中");
        }
    }

}

