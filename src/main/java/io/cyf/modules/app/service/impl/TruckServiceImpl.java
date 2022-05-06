package io.cyf.modules.app.service.impl;

import io.cyf.common.utils.PageCovertUtil;
import io.cyf.modules.app.Dto.TruckDto;
import io.cyf.modules.app.entity.EmployeeEntity;
import io.cyf.modules.app.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    EmployeeService employeeService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TruckEntity> page = this.page(
                new Query<TruckEntity>().getPage(params),
                new QueryWrapper<TruckEntity>()
        );
        IPage<TruckDto> truckDtoIPage = PageCovertUtil.pageVoCovert(page, TruckDto.class);
        List<TruckDto> list = new ArrayList<>();
        page.getRecords().forEach(truckEntity -> {
            TruckDto truckDto = new TruckDto();
            BeanUtils.copyProperties(truckEntity,truckDto);
            EmployeeEntity emp = employeeService.getById(truckDto.getDriverId());
            truckDto.setDriverName(emp.getName());
            list.add(truckDto);
        });
        truckDtoIPage.setRecords(list);
        return new PageUtils(truckDtoIPage);


    }

}