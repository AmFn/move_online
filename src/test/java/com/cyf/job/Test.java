package com.cyf.job;

import com.cyf.modules.app.entity.EmployeeEntity;
import com.cyf.modules.app.entity.OrderEntity;
import com.cyf.modules.app.service.EmployeeService;
import com.cyf.modules.app.service.MoveTypeService;
import com.cyf.modules.app.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenyufeng
 * @date 2022/5/19 10:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Test {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    MoveTypeService moveTypeService;
    @Autowired
    OrderService orderService;

    @org.junit.Test
    public void test() {
        List<OrderEntity> orderEntities = orderService.getBaseMapper().selectList(null);
        List<EmployeeEntity> empList = employeeService.getBaseMapper().selectList(null);
        EmployQueue.getInstance().putAll(
                empList.stream()
                        .filter(employeeEntity -> employeeEntity.getStatus() == 1)
                        .collect(Collectors.toList())
        );
        TaskQueue.getInstance().putAll(orderEntities);
        AutoDispatchTaskThread autoDispatchTaskThread = new AutoDispatchTaskThread(orderService, moveTypeService,employeeService);
        autoDispatchTaskThread.run();
    }
}
