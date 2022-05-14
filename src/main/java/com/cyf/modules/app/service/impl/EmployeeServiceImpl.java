package com.cyf.modules.app.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyf.common.utils.EmpExcelListener;
import com.cyf.modules.app.service.EmployeeService;
import com.cyf.common.utils.PageUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyf.common.utils.Query;

import com.cyf.modules.app.dao.EmployeeDao;
import com.cyf.modules.app.entity.EmployeeEntity;
import org.springframework.web.multipart.MultipartFile;

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

    @Override
    public void saveByExcel(MultipartFile file,EmployeeService service) {
        InputStream in = null;
        try {
            in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, EmployeeEntity.class,new EmpExcelListener(service)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}