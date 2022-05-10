/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.cyf.modules.app.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.cyf.common.exception.RRException;
import io.cyf.common.utils.R;
import io.cyf.common.validator.ValidatorUtils;
import io.cyf.modules.app.entity.UserEntity;
import io.cyf.modules.app.form.RegisterForm;
import io.cyf.modules.app.service.UserService;
import io.cyf.modules.sys.service.SysCaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 注册
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/app")
@Api("APP注册接口")
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
