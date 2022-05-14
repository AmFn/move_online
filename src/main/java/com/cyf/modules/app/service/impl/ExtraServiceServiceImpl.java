package com.cyf.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyf.modules.app.service.ExtraServiceService;
import org.springframework.stereotype.Service;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyf.common.utils.PageUtils;
import com.cyf.common.utils.Query;

import com.cyf.modules.app.dao.ExtraServiceDao;
import com.cyf.modules.app.entity.ExtraServiceEntity;
import org.springframework.util.StringUtils;

@Service("extraServiceService")
public class ExtraServiceServiceImpl extends ServiceImpl<ExtraServiceDao, ExtraServiceEntity> implements ExtraServiceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {



        LambdaQueryWrapper<ExtraServiceEntity> wrapper = new LambdaQueryWrapper<ExtraServiceEntity>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((w) -> {
                w.or().like(ExtraServiceEntity::getName, key)

                ;
            });
        }
        IPage<ExtraServiceEntity> page = this.page(
                new Query<ExtraServiceEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }


}