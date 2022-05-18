package com.atguigu.order.service.impl;

import com.atguigu.commonutils.JWTUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.order_vo.CourseOrder;
import com.atguigu.commonutils.order_vo.MemberOrder;
import com.atguigu.commonutils.order_vo.TeacherOrder;
import com.atguigu.order.client.EduClient;
import com.atguigu.order.client.UcenterClient;
import com.atguigu.order.entity.TOrder;
import com.atguigu.order.mapper.TOrderMapper;
import com.atguigu.order.service.TOrderService;
import com.atguigu.order.utils.OrderNoUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-04-05
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    EduClient eduClient;
    @Autowired
    UcenterClient ucenterClient;

    @Override
    public String saveOrder(String courseId, HttpServletRequest request) {
        /**
         *  1. 根据课程id 查询课程信息
         */
        CourseOrder courseOrder = eduClient.getCourseByIdOrder(courseId);
        /**
         *  2. 根据课程id获取的讲师id，获取讲师对象
         */
        TeacherOrder teacherOrder = eduClient.selectByIdOrder(courseOrder.getTeacherId());
        /**
         *  3. 根据reques中header里的token信息，查新用户id
         */
        String memberId = JWTUtils.getMemberIdByJwtToken(request);
        /**
         *  4. 根据用户id，查询用户信息
         */
        MemberOrder memberOrder = ucenterClient.getMemberById(memberId);
        /**
         *  5. 将用户信息赋值到order对象中
         */
        TOrder order = new TOrder();
        //生成并保存订单号
        String orderNo = OrderNoUtil.getOrderNo();
        order.setOrderNo(orderNo);
        //设置课程id、名称、封面
        order.setCourseId(courseId);
        order.setCourseTitle(courseOrder.getTitle());
        order.setCourseCover(courseOrder.getCover());
        //设置讲师名称
        order.setTeacherName(teacherOrder.getName());
        //设置会员id、昵称、手机
        order.setMemberId(memberOrder.getId());
        order.setNickname(memberOrder.getNickname());
        order.setMobile(memberOrder.getMobile());
        //设置订单金额，就是课程金额
        order.setTotalFee(courseOrder.getPrice());
        //设置支付方式、支付状态、逻辑删除
        order.setPayType(1);
        order.setStatus(0);
        order.setIsDeleted(false);
        /**
         *  5. 保存并返回 订单号 orderNo
         */
        this.save(order);
        return order.getOrderNo();
    }

    @Override
    public int creatIsBuy(String courseId, String memberId) {
        QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.eq("member_id", memberId);
        queryWrapper.eq("status", 1);
        int count = this.count(queryWrapper);
        return count;
    }
}
