package com.cyf.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyf.modules.app.entity.EmployeeOrderEntity;
import com.cyf.common.utils.PageUtils;

import java.util.Map;

/**
 * 员工_订单表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-04-21 17:21:02
 */
public interface EmployeeOrderService extends IService<EmployeeOrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

