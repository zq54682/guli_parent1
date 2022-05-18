package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.CourseRelease;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.info.CourseFrontInfo;
import com.atguigu.eduservice.entity.info.CourseInfo;
import com.atguigu.eduservice.entity.query.CourseFrontQuery;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-03-15
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    EduCourseService eduCourseService;

    /**
     * 保存课程
     * @param courseInfo
     */
    @Override
    public String saveCourseAndDescriptrion(CourseInfo courseInfo) {
        /**
         * 1. 页面的数据以jason的格式传过来，新建个实例用于储存页面数据对象，再复制到课程对象和课程简介对象
         */
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo, eduCourse);
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfo, eduCourseDescription);
        /**
         * 2. 保存课程对象和课程简介对象，给课程简介对象赋值id，使其与课程对象1对1
         */
        baseMapper.insert(eduCourse);
        eduCourseDescription.setId(eduCourse.getId());
        eduCourseDescriptionService.save(eduCourseDescription);

        return eduCourse.getId();

    }


    /**
     * 修改 课程对象 和 课程简介对象
     * @param courseInfo
     * @return
     */
    @Override
    public void updateCourseAndDescription(CourseInfo courseInfo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo, eduCourse);
        baseMapper.updateById(eduCourse);
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfo.getId());
        eduCourseDescription.setDescription(courseInfo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    /**
     * 查询 courseinfo
     * @param id
     * @return
     */
    @Override
    public CourseInfo getCourseInfo(String id) {
        EduCourse eduCourse = baseMapper.selectById(id);
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(id);
        CourseInfo courseInfo = new CourseInfo();
        BeanUtils.copyProperties(eduCourse, courseInfo);
        courseInfo.setDescription(eduCourseDescription.getDescription());
        return courseInfo;
    }

    /**
     * 获取最终发布的页面对象
     * @param id
     */
    @Override
    public CourseRelease getCourseReleases(String id) {
        CourseRelease courseRelease = baseMapper.getCourseRelease(id);
        return courseRelease;
    }

    @Override
    @Cacheable(value = "shouye", key = "'select8Course'")
    public List<EduCourse> getShouyeCourse() {
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("view_count");
        courseQueryWrapper.last("limit 8");
        List<EduCourse> courseList = this.list(courseQueryWrapper);
        return courseList;
    }

    @Override
    public List<EduCourse> getCourseList(String id) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", id);
        List<EduCourse> courses = eduCourseService.list(queryWrapper);
        return courses;
    }

    @Override
    public Map<String, Object> getCoursePageListByTid(String id, long page, long limit) {
        /**
         * 创建 分页对象
         */
        Page<EduCourse> pageC = new Page<>(page, limit);
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", id);
        /**
         * 查询分页
         */
        baseMapper.selectPage(pageC, queryWrapper);
        /**
         * 获取当前页的数据 和 总数
         */
        List<EduCourse> courses = pageC.getRecords();
        long total = pageC.getTotal();

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("courses", courses);
        return map;
    }

    /**
     * 多条件分页查询
     * @return
     */
    @Override
    public HashMap<String, Object> getCourseListByMore(long page, long limit, CourseFrontQuery queryVo) {
        /**
         * 1. 创建 分页对象
         */
        Page<EduCourse> pageC = new Page<>(page, limit);
        /**
         * 2. 判断条件值是否为空，不为空拼接
         */
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(queryVo.getSubjectParentId())) { //一级分类
            queryWrapper.eq("subject_parent_id",queryVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(queryVo.getSubjectId())) { //二级分类
            queryWrapper.eq("subject_id",queryVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(queryVo.getBuyCountSort())) { //关注度
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(queryVo.getGmtCreateSort())) { //最新
            queryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(queryVo.getPriceSort())) {//价格
            queryWrapper.orderByDesc("price");
        }
        /**
         * 3. 查询分页
         */
        baseMapper.selectPage(pageC, queryWrapper);
        /**
         * 4. 获取分页信息
         */
        List<EduCourse> list = pageC.getRecords();
        long total = pageC.getTotal();

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("list", list);
        return map;
    }

    @Override
    public CourseFrontInfo getCourseFrontInfoByCId(String courseId) {
        CourseFrontInfo courseFrontInfo = baseMapper.getCourseFrontInfo(courseId);
        return courseFrontInfo;
    }


}
