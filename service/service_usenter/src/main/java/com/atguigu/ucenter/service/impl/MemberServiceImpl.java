package com.atguigu.ucenter.service.impl;

import com.atguigu.commonutils.JWTUtils;
import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.entity.vo.LoginVo;
import com.atguigu.ucenter.entity.vo.RegisterVo;
import com.atguigu.ucenter.mapper.MemberMapper;
import com.atguigu.ucenter.service.MemberService;
import com.atguigu.ucenter.utils.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-03-29
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    /**
     * 注册用户
     * @param registerVo
     * @return
     */
    @Override
    public boolean register(RegisterVo registerVo) {
        /**
         *  1. 获取信息，并校验参数不为空
         */
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        if (StringUtils.isEmpty(nickname) ||
                StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)){
            return false;
        }
        /**
         *  2. 校验 验证码
         */
        // 获取redis中的验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)){
            return false;
        }
        /**
         *  3. 校验当前手机是否已被注册
         */
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count >= 1){
            return false;
        }
        /**
         *  4. 添加注册信息到数据表中
         */
        Member member = new Member();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setIsDeleted(false);
        member.setAvatar("https://atguigu-eduteacher-zq.oss-cn-shenzhen.aliyuncs.com/2022/03/24/e36ca22bca484810bbd1e94affb12fa4/teacher/1442297969808.jpg");
        boolean save = this.save(member);
        System.out.println(save);
        return save;
    }

    /**
     * 登陆
     * @param loginVo
     * @return
     */
    @Override
    public String login(LoginVo loginVo) {
        /**
         *  1. 获取用户名(手机号)、密码，并校验是否为空
         */
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            return null;
        }
        /**
         *  2. 获取用户
         */
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        Member member = baseMapper.selectOne(queryWrapper);
        if (member == null){
            return null;
        }
        /**
         *  3. 校验密码
         */
        String passwordMD5 = member.getPassword();
        String passwordLogin = MD5.encrypt(password);
        if (!passwordMD5.equals(passwordLogin)){
            return null;
        }
        /**
         *  4. 校验是否被禁用
         */
        if (member.getIsDisabled()){
            return null;
        }
        String token = JWTUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

    @Override
    public Integer zhuceCountByDay(String day) {
        Integer count = baseMapper.selectCountByDay(day);
        return count;
    }
}
