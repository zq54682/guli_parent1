package com.atguigu.ucenter.controller;

import com.atguigu.commonutils.JWTUtils;
import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.service.MemberService;
import com.atguigu.ucenter.utils.ConstantPropertiesUtil;
import com.atguigu.ucenter.utils.HttpClientUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WXApiController {

    @Autowired
    MemberService memberService;


    /**
     * 第二步
     * 微信扫码后跳转，并且获取用户信息
     * code 是微信的一个临时票据，类似于验证码
     * state 是自定义的参数，这里我们在生成二维码的方法里自定义的是 "atguigu"，这个数据不是非常重要
     */
    @GetMapping("/callback")
    public String callback(String code, String state){
        /**
         * 1. 获取 code 值，利用 code 访问微信的额一个固定地址，得到 access_token，openid 两个值
         *      openid 就是微信的一个唯一标识，在用户数据表中也有这个字段
         */
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        /**
         * 2. 拼接三个参数：id  秘钥 和 code
         */
        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);
        try {
            /**
             * 3. 请求这个拼接好的地址，得到返回两个值 accsess_token 和 openid
             *      使用httpclient发送请求，得到返回结果
             */
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            /**
             * 4. 从accessTokenInfo字符串获取出来两个值 accsess_token 和 openid
             *      把accessTokenInfo字符串转换map集合，根据map里面key获取对应值
             *      使用json转换工具 Gson
             */
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String)mapAccessToken.get("access_token");
            String openid = (String)mapAccessToken.get("openid");
            /**
             * 5. 把微信用户添加在数据库中
             *      先查询数据库中是否已经有这个数据
             *      openid 就是微信的一个唯一标识，在用户数据表中也有这个字段
             */
            QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("openid", openid);
            Member member = memberService.getOne(queryWrapper);
            if (member==null){
                /**
                 * 6. 拿着得到accsess_token 和 openid，再去请求微信提供固定的地址，获取到扫描人信息
                 *      访问微信的资源服务器，获取用户信息
                 */
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //拼接两个参数
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                //发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);
                //获取返回userinfo字符串扫描人信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String)userInfoMap.get("nickname");//昵称
                String headimgurl = (String)userInfoMap.get("headimgurl");//头像
                //保存入库
                member = new Member();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                member.setIsDeleted(false);
                member.setIsDisabled(false);
                memberService.save(member);
            }
            /**
             * 7. 如果存在(存在的话也可以更新头像，昵称的信息)，就保存到cookie
             *      * 但是cookie不能跨域，所以我们使用token来解决跨域问题
             */
            String jwtToken = JWTUtils.getJwtToken(member.getId(), member.getNickname());
            System.out.println(jwtToken);
            /**
             * 8. 最后：返回首页面，通过路径传递token字符串
             */
            return "redirect:http://localhost:3000?token="+jwtToken;

        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }



    /**
     * 第一步
     * 生成微信二维码
     * @return
     */
    @GetMapping("/login")
    public String wxLogin(){
        /**
         * 1. 微信开放平台授权的 url
         */
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        /**
         * 2. 对 redirect_url 进行 URLEncoder 编码
         */
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        }catch (Exception e){
        }
        /**
         * 3. 设置 %s 里面的值
         */
        String url = String.format(baseUrl, ConstantPropertiesUtil.WX_OPEN_APP_ID, redirectUrl, "atguigu");

        /**
         * 4.  return "redirect:" 重定向到指定页面，就是请求微信地址
         */
        return "redirect:" + url;
    }


}
