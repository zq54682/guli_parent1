package com.atguigu.ucenter.mapper;

import com.atguigu.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2022-03-29
 */
public interface MemberMapper extends BaseMapper<Member> {

    Integer selectCountByDay(String day);
}
