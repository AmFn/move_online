package io.cyf.modules.app.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.Query;

import io.cyf.modules.app.dao.CompensateDao;
import io.cyf.modules.app.entity.CompensateEntity;
import io.cyf.modules.app.service.CompensateService;


@Service("compensateService")
public class CompensateServiceImpl extends ServiceImpl<CompensateDao, CompensateEntity> implements CompensateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CompensateEntity> page = this.page(
                new Query<CompensateEntity>().getPage(params),
                new QueryWrapper<CompensateEntity>()
        );

        return new PageUtils(page);
    }

}