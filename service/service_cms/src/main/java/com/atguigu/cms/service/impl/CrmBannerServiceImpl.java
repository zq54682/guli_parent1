package com.atguigu.cms.service.impl;

import com.atguigu.cms.entity.CrmBanner;
import com.atguigu.cms.mapper.CrmBannerMapper;
import com.atguigu.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-03-26
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    /**
     * @Cacheable(value = "banner", key = "'selectAllBanner'")
     * 表示数据放入 redis 缓存中，下次请求会优先在缓存中找数据，如果缓存中没有，再查找mysql数据库
     * @return
     */
    @Override
    @Cacheable(value = "banner", key = "'selectAllBanner'")
    public List<CrmBanner> getAll() {
        QueryWrapper<CrmBanner> queryWrapper = new QueryWrapper<>();
        // 根据 id 进行排序
        queryWrapper.orderByDesc("id");
        // 只查询前面的两条数据
        // last 方法，拼接sql
        queryWrapper.last("limit 2");
        List<CrmBanner> list = baseMapper.selectList(queryWrapper);
        return list;
    }
}
