package io.cyf.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.cyf.common.utils.PageUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.Query;

import io.cyf.modules.app.dao.EmployeeDao;
import io.cyf.modules.app.entity.EmployeeEntity;
import io.cyf.modules.app.service.EmployeeService;


@Service("employeeService")
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, EmployeeEntity> implements EmployeeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<EmployeeEntity> page = this.page(
                new Query<EmployeeEntity>().getPage(params),
                new QueryWrapper<EmployeeEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<EmployeeEntity> getDrivers() {
        LambdaQueryWrapper<EmployeeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EmployeeEntity::getType,1);
        return baseMapper.selectList(wrapper);

    }

    @Override
    public List<EmployeeEntity> getIdleEmployee() {
        LambdaQueryWrapper<EmployeeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(EmployeeEntity::getType,1);
        wrapper.eq(EmployeeEntity::getStatus,1);
        return baseMapper.selectList(wrapper);
    }
}