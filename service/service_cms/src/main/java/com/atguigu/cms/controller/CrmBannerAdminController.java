package com.atguigu.cms.controller;


import com.atguigu.cms.entity.CrmBanner;
import com.atguigu.cms.service.CrmBannerService;
import com.atguigu.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台 banner
 */

@RestController
@RequestMapping("/cmsservice/cmsbanner/admin")
@CrossOrigin
public class CrmBannerAdminController {

    @Autowired
    CrmBannerService crmBannerService;

    /**
     * 查询所有 banner
     * @return
     */
    @GetMapping("/getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> list = crmBannerService.list(null);
        return R.ok().data("list", list);
    }

    /**
     * 查询一个banner
     */
    @GetMapping("/getBannerById/{id}")
    public R getBannerById(@PathVariable String id){
        CrmBanner crmBanner = crmBannerService.getById(id);
        return R.ok().data("crmBanner", crmBanner);
    }

    /**
     * 新增banner
     * @param crmBanner
     * @return
     */
    @PostMapping("/saveBanner")
    public R saveBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    /**
     * 删除 banner
     */
    @DeleteMapping("/deleteBanner/{id}")
    public R deleteBanner(@PathVariable String id){
        crmBannerService.removeById(id);
        return R.ok();
    }

    /**
     * 修改 banner
     */
    @PostMapping("/updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.updateById(crmBanner);
        return R.ok();
    }


}
