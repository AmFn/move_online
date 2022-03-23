package io.cyf.modules.app.dao;

import io.cyf.modules.app.entity.TruckEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 货车表
 * 
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:15
 */
@Mapper
public interface TruckDao extends BaseMapper<TruckEntity> {
	
}
