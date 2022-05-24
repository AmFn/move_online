/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.cyf.modules.app.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyf.modules.app.entity.UserEntity;
import com.cyf.modules.sys.service.SysCaptchaService;
import com.cyf.common.exception.RRException;
import com.cyf.common.utils.R;
import com.cyf.common.validator.ValidatorUtils;
import com.cyf.modules.app.form.RegisterForm;
import com.cyf.modules.app.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/app")
@Api(tags="注册接口")
public class AppRegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private SysCaptchaService sysCaptchaService;
    @PostMapping("register")
    @ApiOperation("注册")
    public R register(@RequestBody RegisterForm form){
        ValidatorUtils.validateEntity(form);
        UserEntity one = userService.getOne(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getPhone, form.getPhone()));
        if(one!=null){
            throw new RRException("手机号已存在");
        }

        boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
		if(!captcha){
			return R.error("验证码不正确");
		}
        UserEntity user = new UserEntity();
        user.setPhone(form.getPhone());
        user.setUsername(form.getPhone());
        user.setPassword(DigestUtils.sha256Hex(form.getPassword()));
        user.setAvatar("https://s1.ax1x.com/2022/05/01/OCCr4J.jpg");
        userService.save(user);
        return R.ok();
    }
}
