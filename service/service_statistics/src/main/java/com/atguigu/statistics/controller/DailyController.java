package com.atguigu.statistics.controller;


import com.atguigu.commonutils.R;
import com.atguigu.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-04-07
 */
@RestController
@RequestMapping("/statisticsservice/daily")
@CrossOrigin
public class DailyController {

    @Autowired
    DailyService dailyService;

    /**
     * 统计某一天的数据
     */
    @PostMapping("/createStatisticsByDay/{day}")
    public R createStatisticsByDay(@PathVariable String day){
        dailyService.createStatisticsByDay(day);
        return R.ok();
    }

    /**
     * 查询某个区间的统计数据
     */
    @GetMapping("/getStatisticsData/{type}/{begin}/{end}")
    public R getStatisticsData(@PathVariable("type") String type, @PathVariable("begin") String begin, @PathVariable("end") String end){
        Map<String,Object> map = dailyService.getStatisticsDataByType(type,begin,end);
        return R.ok().data(map);
    }


}

