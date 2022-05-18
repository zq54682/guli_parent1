package com.atguigu.statistics.client.impl;

import com.atguigu.commonutils.R;
import com.atguigu.statistics.client.UcenterClient;
import org.springframework.stereotype.Component;


@Component
public class UcenterClientImpl implements UcenterClient {
    @Override
    public R zhuceCount(String day) {
        return R.error().message("获取用户失败！");
    }
}
