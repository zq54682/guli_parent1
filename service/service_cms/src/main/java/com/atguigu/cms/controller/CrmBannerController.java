package com.atguigu.cms.controller;


import com.atguigu.cms.entity.CrmBanner;
import com.atguigu.cms.service.CrmBannerService;
import com.atguigu.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-03-26
 */
@RestController
@RequestMapping("/cmsservice/cmsbanner/front")
@CrossOrigin
public class CrmBannerController {

    @Autowired
    CrmBannerService crmBannerService;

    /**
     * 查询所有 banner
     */
    @GetMapping("/getAll")
    public R getAll(){
        List<CrmBanner> list = crmBannerService.getAll();
        return R.ok().data("list", list);
    }

}

