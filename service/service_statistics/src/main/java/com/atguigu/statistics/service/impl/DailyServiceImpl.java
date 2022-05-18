package com.atguigu.statistics.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.statistics.client.UcenterClient;
import com.atguigu.statistics.entity.Daily;
import com.atguigu.statistics.mapper.DailyMapper;
import com.atguigu.statistics.service.DailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-04-07
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    UcenterClient ucenterClient;

    @Override
    public void createStatisticsByDay(String day) {
        /**
         * 1. 获取统计数据
         */
        R r = ucenterClient.zhuceCount(day);
        Integer zhuceCount = (Integer) r.getData().get("zhuceCount");
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO
        /**
         * 2. 创建统计对象
         */
        Daily daily = new Daily();
        daily.setRegisterNum(zhuceCount);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);
        /**
         * 3. 查询表中是否存在指定时间的数据，没有就创建，有就更新
         */
        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        // date_calculated 字段是 统计日期
        queryWrapper.eq("date_calculated", day);
        int count = this.count(queryWrapper);
        if (count>0){
            this.update(daily, null);
        }else {
            this.save(daily);
        }

    }

    @Override
    public Map<String, Object> getStatisticsDataByType(String type, String begin, String end) {
        /**
         * 1. 封装查询条件，我们只需查询两组数据，x轴的日期，y轴的指定type数量
         */
        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date_calculated", begin, end);
        queryWrapper.select("date_calculated",type);
        List<Daily> dailies = baseMapper.selectList(queryWrapper);
        /**
         * 2. 创建 x轴(时间) y轴(数量) 集合
         */
        List<String> xData = new ArrayList<>();
        List<Integer> yData = new ArrayList<>();
        /**
         * 封装  x y 轴数据
         */
        switch (type){
            case "login_num":
                for (Daily daily : dailies) {
                    xData.add(daily.getDateCalculated());
                    yData.add(daily.getLoginNum());
                }
                break;
            case "register_num":
                for (Daily daily : dailies) {
                    xData.add(daily.getDateCalculated());
                    yData.add(daily.getRegisterNum());
                }
                break;
            case "video_view_num":
                for (Daily daily : dailies) {
                    xData.add(daily.getDateCalculated());
                    yData.add(daily.getVideoViewNum());
                }
                break;
            case "course_num":
                for (Daily daily : dailies) {
                    xData.add(daily.getDateCalculated());
                    yData.add(daily.getCourseNum());
                }
                break;
            default:
                break;
        }

        Map<String,Object> map = new HashMap<>();
        map.put("xData", xData);
        map.put("yData", yData);
        return map;
    }
}
