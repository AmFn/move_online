package io.cyf.modules.app.dao;

import io.cyf.modules.app.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表
 * 
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
	
}
