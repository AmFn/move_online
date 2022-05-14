package com.cyf.common.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyf.common.exception.RRException;
import com.cyf.modules.app.entity.EmployeeEntity;
import com.cyf.modules.app.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class EmpExcelListener extends AnalysisEventListener<EmployeeEntity> {

    public EmployeeService employeeService;
    public EmpExcelListener() {}
    public EmpExcelListener(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //创建list集合封装最终的数据
    List<EmployeeEntity> list = new ArrayList<>();
        //一行一行去读取excle内容
    @Override
    public void invoke(EmployeeEntity data, AnalysisContext analysisContext) {
        list.add(data);
    }
        //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
//      log.info("表头信息：{}",headMap);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("list：{}",list);
        list.forEach(employeeEntity -> {
            employeeEntity.setIsDel(0);
            employeeEntity.setStatus(1);
            EmployeeEntity one = employeeService.getBaseMapper().selectOne(new LambdaQueryWrapper<EmployeeEntity>().eq(EmployeeEntity::getPhone, employeeEntity.getPhone()));
            if(one!=null){
               return;
            }

            employeeService.getBaseMapper().insert(employeeEntity);


        });
    }

}