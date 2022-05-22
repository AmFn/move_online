package com.cyf.modules.app.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.cyf.modules.app.entity.EmployeeEntity;
import com.cyf.modules.app.entity.OrderEntity;
import com.cyf.modules.app.service.EmployeeService;
import com.cyf.modules.app.service.OrderService;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chenyufeng
 * @date 2022/5/19 12:31
 */
@Component
public class JobSchedule {
    @Autowired
    OrderService orderService;
    @Autowired
    EmployeeService employeeService;
//    @Scheduled(cron = "0/5 * * * * *")
    public  void addOrder(){
        TaskQueue.getInstance().put(orderService.getOne(new LambdaQueryWrapper<OrderEntity>().eq(OrderEntity::getId,2L)));
        if(EmployQueue.getInstance().getSize()<=0){
            List<EmployeeEntity> list = employeeService.list(null);
            EmployQueue.getInstance().putAll(list);
        }
    }
}
