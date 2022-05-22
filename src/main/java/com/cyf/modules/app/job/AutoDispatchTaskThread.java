package com.cyf.modules.app.job;

import com.cyf.modules.app.Dto.AssignEmployeeDto;
import com.cyf.modules.app.entity.EmployeeEntity;
import com.cyf.modules.app.entity.OrderEntity;
import com.cyf.modules.app.service.EmployeeService;
import com.cyf.modules.app.service.MoveTypeService;
import com.cyf.modules.app.service.OrderService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动派单
 */
@Slf4j
public class AutoDispatchTaskThread implements Runnable {
    private boolean stop = false;
    private OrderService orderService;
    private MoveTypeService moveTypeService;

    public AutoDispatchTaskThread(OrderService orderService, MoveTypeService moveTypeService, EmployeeService employeeService) {
        this.orderService = orderService;
        this.moveTypeService = moveTypeService;
    }

    @Override
    public void run() {
        log.info("AutoDispatchTaskThread init TaskQueue size :{},EmployQueue size:{}", TaskQueue.getInstance().getSize(), EmployQueue.getInstance().getSize());
        try {
            while (!stop) {
                OrderEntity order = TaskQueue.getInstance().take();
                int assigned = orderService.getAssignedEmployeeCount(order.getId());
                Integer requirePeople = moveTypeService.getById(order.getMoveTypeId()).getRequirePeople();
                if ((requirePeople - assigned) <= 0) {
                    log.info("已分配满");
                    break;
                }
                if (EmployQueue.getInstance().getSize() < requirePeople) {
                    throw new RuntimeException("员工人数不够，请添加员工后重新分配");
                } else {
                    List<Long> empIds = new ArrayList<>();
                    for (int i = 0; i < requirePeople; i++) {
                        EmployeeEntity emp = EmployQueue.getInstance().takeNext();
                        empIds.add(emp.getId());
                        emp.setStatus(0);
                    }
                    log.info("订单{}分配员工列表：{}", order.getId(), empIds);
                    AssignEmployeeDto assignEmployeeDto = new AssignEmployeeDto();
                    assignEmployeeDto.setEmpIds(empIds);
                    assignEmployeeDto.setId(order.getId());
                    orderService.assignEmployee(assignEmployeeDto);
                }
            }
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
        }
    }
}