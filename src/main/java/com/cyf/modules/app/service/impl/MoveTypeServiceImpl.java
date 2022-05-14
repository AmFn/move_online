package com.cyf.modules.app.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyf.modules.app.service.MoveTypeService;
import com.cyf.common.utils.PageUtils;
import com.cyf.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.cyf.modules.app.dao.MoveTypeDao;
import com.cyf.modules.app.entity.MoveTypeEntity;
import org.springframework.util.StringUtils;

@Service("moveTypeService")
public class MoveTypeServiceImpl extends ServiceImpl<MoveTypeDao, MoveTypeEntity> implements MoveTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<MoveTypeEntity> wrapper = new LambdaQueryWrapper<MoveTypeEntity>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((w) -> {
                w.or().like(MoveTypeEntity::getType, key)

                ;
            });
        }
        IPage<MoveTypeEntity> page = this.page(
                new Query<MoveTypeEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

}