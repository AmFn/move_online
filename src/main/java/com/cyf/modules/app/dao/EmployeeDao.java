package com.cyf.modules.app.dao;

import com.cyf.modules.app.entity.EmployeeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工表
 * 
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
@Mapper
public interface EmployeeDao extends BaseMapper<EmployeeEntity> {
	
}
