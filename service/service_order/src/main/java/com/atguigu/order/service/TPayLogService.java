package com.atguigu.order.service;

import com.atguigu.order.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-04-06
 */
public interface TPayLogService extends IService<TPayLog> {

    Map creatPayCode(String orderNo);

    Map<String,String> queryPayStatus(String orderNo);

    void updateOrderStatus(Map<String,String>  map);
}
