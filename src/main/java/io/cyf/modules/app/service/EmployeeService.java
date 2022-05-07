package io.cyf.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cyf.common.utils.PageUtils;
import io.cyf.modules.app.entity.EmployeeEntity;

import java.util.List;
import java.util.Map;

/**
 * 员工表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
public interface EmployeeService extends IService<EmployeeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<EmployeeEntity> getDrivers();

    List<EmployeeEntity> getIdleEmployee();
}

