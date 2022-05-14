package com.cyf.modules.app.dao;

import com.cyf.modules.app.entity.EmployeeOrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工_订单表
 * 
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-04-21 17:21:02
 */
@Mapper
public interface EmployeeOrderDao extends BaseMapper<EmployeeOrderEntity> {
	
}
