/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.cyf.modules.app.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cyf.common.exception.RRException;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.Query;
import io.cyf.common.validator.Assert;
import io.cyf.modules.app.dao.UserDao;
import io.cyf.modules.app.entity.UserEntity;
import io.cyf.modules.app.form.LoginForm;
import io.cyf.modules.app.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

	@Override
	public UserEntity queryByMobile(String mobile) {
		return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("phone", mobile));
	}

	@Override
	public long login(LoginForm form) {
		UserEntity user = queryByMobile(form.getMobile());
		Assert.isNull(user, "手机号或密码错误");

		//密码错误
		if(!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))){
			throw new RRException("手机号或密码错误");
		}

		return user.getId();
	}

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<UserEntity> page = this.page(
				new Query<UserEntity>().getPage(params),
				new QueryWrapper<UserEntity>()
		);

		return new PageUtils(page);
	}

	@Override
	public boolean updateAddr(Long oldAddrId, Long newAddrId,Long uid) {
		UserEntity userEntity = baseMapper.selectById(uid);
		userEntity.setNewAddressId(newAddrId);
		userEntity.setAddressId(oldAddrId);
		int id = baseMapper.updateById(userEntity);
		return id > 0;
	}
}
