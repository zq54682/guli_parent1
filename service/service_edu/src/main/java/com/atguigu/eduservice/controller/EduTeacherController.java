package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.commonutils.order_vo.TeacherOrder;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.query.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-03-03
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin    // 解决简单的跨域问题
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    /**
     * 查询所有讲师
     * @return
     */
    @GetMapping("findall")
    public R findAllTeacher(){
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("AllTeacher", list);
    }

    /**
     * 根据 id 删除
     * @param id
     * @return
     */
    @DeleteMapping("deleteone/{id}")
    public R deleteTeacherById(@PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if (flag){
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("{page}/{limit}")
    public R pageTeacher(@PathVariable long page,
                         @PathVariable long limit){
        /**
         * 创建分页对象, 参数使用请求中传递的
         */
        Page<EduTeacher> pageT = new Page<>(page, limit);
        /**
         * 执行分页过程
         */
        teacherService.page(pageT, null);
        /**
         * 当前页的数据集合
         */
        List<EduTeacher> records = pageT.getRecords();
        /**
         * 总数据
         */
        long total = pageT.getTotal();
        /**
         * 创建个Map，用于传递
         * 或者使用下面的方法：
         *      return R.ok().data("total", total).data("data", records);
         */
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("data", records);
        return R.ok().data(map);
    }


    /**
     * 多条件联合分页查询
     */
    @PostMapping("pageQurey/{page}/{limit}")
    public R pageQurey(@PathVariable long page,
                       @PathVariable long limit,
                       @RequestBody(required = false) TeacherQuery teacherQuery){
        /**
         * 1. 创建分页对象, 参数使用请求中传递的
         */
        Page<EduTeacher> pageT = new Page<>(page, limit);
        /**
         * 2. 构建查询条件
         */
        // queryWrapper 是 mybatis plus 中实现查询的对象封装操作类
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        // 根据指定表字段排序
        queryWrapper.orderByAsc("name");
        // 如果条件不为空，需要组合条件
        System.out.println("teacherQuery 不为null");
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        // 如果条件中的 name 值，不为null或者不为空，那么表中name字段按照name值来模糊查询
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(name)) {
            //构建条件
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create",end);
        }
        /**
         * 排序
         */
        queryWrapper.orderByDesc("gmt_create");
        /**
         * 3. 执行分页过程,带上查询条件
         */
        teacherService.page(pageT, queryWrapper);
        /**
         * 获取分页后的信息
         */
        long total = pageT.getTotal();
        List<EduTeacher> records = pageT.getRecords();
        return R.ok().data("total",total).data("data",records);
    }

    /**
     * 添加方法
     * @param eduTeacher
     * @return
     */
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody(required = false) EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    @GetMapping("selectById/{id}")
    public R selectById(@PathVariable("id") String id){
        EduTeacher data = teacherService.getById(id);
        return R.ok().data("data",data);
    }

    /**
     * 根据 id 修改数据
     * @return
     */
    @PutMapping("updateTeacher/{id}")
    public R updateTeacher(@PathVariable String id,
                           @RequestBody(required = false) EduTeacher eduTeacher){
        eduTeacher.setId(id);
        teacherService.updateById(eduTeacher);
        return R.ok();
    }

    /**
     * 根据id查询数据
     */
    @GetMapping("selectByIdOrder/{id}")
    public TeacherOrder selectByIdOrder(@PathVariable("id") String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        TeacherOrder teacherOrder = new TeacherOrder();
        BeanUtils.copyProperties(eduTeacher, teacherOrder);
        return teacherOrder;
    }


}

