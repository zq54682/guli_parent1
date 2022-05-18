package com.atguigu.msm.service.impl;

import com.atguigu.msm.service.Msmservice;
import com.atguigu.msm.utils.RandomUtil;
import com.atguigu.msm.utils.SendUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class MsmserviceImpl implements Msmservice {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Override
    public boolean send(String phone) {
        /**
         *  1. 获取6位随机数
         */
        String phoneValue = RandomUtil.getSixBitRandom();
        /**
         *  2. 保存到 Redis 中并设置过期时间, key 相同时会覆盖
         *  void set(K key, V value, long timeout, TimeUnit unit)
         *  key是键，value是值，timeout 过期时间，unit 过期时间单位
         */
        redisTemplate.opsForValue().set(phone, phoneValue, 5, TimeUnit.MINUTES);
        /**
         *  3. 发送短信
         */
        SendUtils sendUtils = new SendUtils();
        boolean send = sendUtils.send(phoneValue, phone);
        return send;
    }



}
