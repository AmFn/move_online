package com.cyf.modules.app.service.impl;

import com.cyf.modules.app.dao.ServiceOrderDao;
import com.cyf.modules.app.entity.ServiceOrderEntity;
import com.cyf.modules.app.service.ServiceOrderService;
import com.cyf.common.utils.PageUtils;
import com.cyf.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service("serviceOrderService")
public class ServiceOrderServiceImpl extends ServiceImpl<ServiceOrderDao, ServiceOrderEntity> implements ServiceOrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ServiceOrderEntity> page = this.page(
                new Query<ServiceOrderEntity>().getPage(params),
                new QueryWrapper<ServiceOrderEntity>()
        );

        return new PageUtils(page);
    }

}