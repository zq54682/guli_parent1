package com.atguigu.order.service;

import com.atguigu.order.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-04-05
 */
public interface TOrderService extends IService<TOrder> {

    String saveOrder(String courseId, HttpServletRequest request);

    int creatIsBuy(String courseId, String memberId);
}
