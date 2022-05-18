package com.atguigu.ucenter.service;

import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.entity.vo.LoginVo;
import com.atguigu.ucenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-03-29
 */
public interface MemberService extends IService<Member> {

    boolean register(RegisterVo registerVo);

    String login(LoginVo loginVo);

    Integer zhuceCountByDay(String day);
}
