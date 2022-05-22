package com.cyf.job;

import com.cyf.modules.app.Dto.AssignEmployeeDto;
import com.cyf.modules.app.entity.EmployeeEntity;
import com.cyf.modules.app.entity.MoveTypeEntity;
import com.cyf.modules.app.entity.OrderEntity;
import com.cyf.modules.app.service.EmployeeService;
import com.cyf.modules.app.service.MoveTypeService;
import com.cyf.modules.app.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 自动派单
 */
@Slf4j
public class AutoDispatchTaskThread implements Runnable {
    private boolean stop = false;

    private OrderService orderService;

    private MoveTypeService moveTypeService;


    public  AutoDispatchTaskThread(OrderService orderService, MoveTypeService moveTypeService,EmployeeService employeeService){
        this.orderService = orderService;
        this.moveTypeService = moveTypeService;
    }
    @Override
    public void run() {
        try {
            while (!stop) {
                while (TaskQueue.getInstance().take() != null) {
                    OrderEntity take = TaskQueue.getInstance().take();
                    Integer requirePeople = moveTypeService.getById(take.getMoveTypeId()).getRequirePeople();
                    if (EmployQueue.getInstance().getSize() < requirePeople) {
                        log.info("员工人数不够，阻塞中。。。");
                    } else {
                        List<Long> empIds = new ArrayList<>();
                        for (int i = 0; i < requirePeople; i++) {
                            EmployeeEntity emp = EmployQueue.getInstance().takeNext();
                            empIds.add(emp.getId());
                            emp.setStatus(0);
                        }
                        log.info("订单{}分配员工列表：{}", take.getId(),empIds);

//                    orderService.assignEmployee(new AssignEmployeeDto());
                    }
                }

            }
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
        }
    }
}