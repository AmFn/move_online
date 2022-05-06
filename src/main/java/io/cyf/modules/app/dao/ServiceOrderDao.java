package io.cyf.modules.app.dao;

import io.cyf.modules.app.entity.ServiceOrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 服务_订单表
 * 
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-04-21 17:21:02
 */
@Mapper
public interface ServiceOrderDao extends BaseMapper<ServiceOrderEntity> {
	
}
