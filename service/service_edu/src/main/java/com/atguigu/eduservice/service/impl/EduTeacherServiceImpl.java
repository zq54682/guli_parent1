package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-03-03
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    @Cacheable(value = "shouye", key = "'select4Teacher'")
    public List<EduTeacher> getShouyeTeacher() {
        QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("sort");
        teacherQueryWrapper.last("limit 4");
        List<EduTeacher> teacherList = this.list(teacherQueryWrapper);
        return teacherList;
    }

    /**
     * 前台讲师页面分页
     * @param page
     * @param limit
     * @return
     */
    @Override
    public Map<String, Object> getTeacherPage(long page, long limit) {
        /**
         * 创建分页对象
         */
        Page<EduTeacher> pageT = new Page<>(page, limit);
        /**
         * 查询分页
         */
        baseMapper.selectPage(pageT, null);
        /**
         * 获取当前页的数据 和 总数
         */
        List<EduTeacher> teacherList = pageT.getRecords();
        long total = pageT.getTotal();

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("teacherList", teacherList);
        return map;
    }
}
