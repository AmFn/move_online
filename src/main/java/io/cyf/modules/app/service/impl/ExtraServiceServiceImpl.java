package io.cyf.modules.app.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.Query;

import io.cyf.modules.app.dao.ExtraServiceDao;
import io.cyf.modules.app.entity.ExtraServiceEntity;
import io.cyf.modules.app.service.ExtraServiceService;


@Service("extraServiceService")
public class ExtraServiceServiceImpl extends ServiceImpl<ExtraServiceDao, ExtraServiceEntity> implements ExtraServiceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ExtraServiceEntity> page = this.page(
                new Query<ExtraServiceEntity>().getPage(params),
                new QueryWrapper<ExtraServiceEntity>()
        );

        return new PageUtils(page);
    }

}