package io.cyf.modules.app.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.Query;

import io.cyf.modules.app.dao.TruckDao;
import io.cyf.modules.app.entity.TruckEntity;
import io.cyf.modules.app.service.TruckService;


@Service("truckService")
public class TruckServiceImpl extends ServiceImpl<TruckDao, TruckEntity> implements TruckService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TruckEntity> page = this.page(
                new Query<TruckEntity>().getPage(params),
                new QueryWrapper<TruckEntity>()
        );

        return new PageUtils(page);
    }

}