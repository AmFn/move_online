package com.cyf.modules.app.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyf.common.utils.SpringContextUtils;
import com.cyf.modules.app.entity.EmployeeEntity;
import com.cyf.modules.app.service.EmployeeService;
import com.cyf.modules.app.service.MoveTypeService;
import com.cyf.modules.app.service.OrderService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.List;

@Component
@Order(value = 1)
@Slf4j
public class AfterRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        EmployeeService employeeService = (EmployeeService) SpringContextUtils.getBean("employeeService");
        MoveTypeService moveTypeService = (MoveTypeService)SpringContextUtils.getBean("moveTypeService");
        LambdaQueryWrapper<EmployeeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(EmployeeEntity::getType,1);
        List<EmployeeEntity> empList = employeeService.getBaseMapper().selectList(wrapper);
        EmployQueue.getInstance().putAll(empList);
        OrderService orderService = (OrderService)SpringContextUtils.getBean("orderService");
        AutoDispatchTaskThread autoDispatchTaskThread = new AutoDispatchTaskThread(orderService, moveTypeService, employeeService);

        autoDispatchTaskThread.run();
    }
}