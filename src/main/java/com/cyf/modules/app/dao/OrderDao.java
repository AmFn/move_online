package com.cyf.modules.app.dao;

import com.cyf.modules.app.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单表
 * 
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:15
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
