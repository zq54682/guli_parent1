package com.atguigu.statistics;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @EnableDiscoveryClient    nacos里面的注解，让注册中心能够发现，扫描到该服务
 * @EnableFeignClients nacos里面的注解，告诉框架扫描所有使用注解@FeignClient定义的feign客户端，并把feign客户端注册到IOC容器中
 * @EnableScheduling 支持定时器
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling   //@EnableScheduling 支持定时器
public class StatisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class, args);
    }

}
