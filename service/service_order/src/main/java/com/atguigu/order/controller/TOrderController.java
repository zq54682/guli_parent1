package com.atguigu.order.controller;


import com.atguigu.commonutils.R;
import com.atguigu.order.entity.TOrder;
import com.atguigu.order.service.TOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-04-05
 */
@RestController
@RequestMapping("/orderservice/order")
@CrossOrigin
public class TOrderController {

    @Autowired
    TOrderService orderService;

    /**
     * 创建订单
     */
    @PostMapping("/saveOrderByCId/{courseId}")
    public R saveOrderByCId(@PathVariable String courseId, HttpServletRequest request){
        String orderNo = orderService.saveOrder(courseId, request);
        return R.ok().data("orderNo", orderNo);
    }

    /**
     * 根据订单号查询订单
     */
    @PostMapping("/getOrderByONo/{orderNo}")
    public R getOrderByONo(@PathVariable("orderNo") String orderNo){
        QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        TOrder order = orderService.getOne(queryWrapper);
        return R.ok().data("order", order);
    }

    /**
     * 查询是否购买
     */
    @GetMapping("/creatCourseIsBuy/{courseId}/{memberId}")
    public R creatCourseIsBuy(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId){
        int count = orderService.creatIsBuy(courseId,memberId);
        if (count>0){
            System.out.println("isbuy ============== true");
            return R.ok().data("isBuy", true);
        }else {
            return R.ok().data("isBuy", false);
        }
    }

}

