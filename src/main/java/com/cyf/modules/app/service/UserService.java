/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.cyf.modules.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cyf.modules.app.Dto.OrderInfoDto;
import com.cyf.modules.app.entity.UserEntity;
import com.cyf.common.utils.PageUtils;
import com.cyf.modules.app.Dto.UserAddressDto;
import com.cyf.modules.app.Dto.UserAddressLocationDto;
import com.cyf.modules.app.form.LoginForm;

import java.util.List;
import java.util.Map;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface UserService extends IService<UserEntity> {

	UserEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回用户ID
	 */
	long login(LoginForm form);

	PageUtils queryPage(Map<String, Object> params);

    boolean updateAddr(Long oldAddrId, Long newAddrId,Long uid);

    UserEntity getUserInfo();

	void disableUser(Long id);

	void enableUser(Long id);

	UserEntity getByAddressId(Long id);

	PageUtils getAddressDtoPage(Map<String, Object> params);

	UserAddressDto getAddressInfo(Long uid);

	void updateLocation(UserAddressLocationDto locationDto);

    List<OrderInfoDto> getOrders(Long id);
}
