package io.cyf.modules.app.service.impl;

import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import io.cyf.modules.app.dao.EmployeeOrderDao;
import io.cyf.modules.app.entity.EmployeeOrderEntity;
import io.cyf.modules.app.service.EmployeeOrderService;


@Service("employeeOrderService")
public class EmployeeOrderServiceImpl extends ServiceImpl<EmployeeOrderDao, EmployeeOrderEntity> implements EmployeeOrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<EmployeeOrderEntity> page = this.page(
                new Query<EmployeeOrderEntity>().getPage(params),
                new QueryWrapper<EmployeeOrderEntity>()
        );

        return new PageUtils(page);
    }

}