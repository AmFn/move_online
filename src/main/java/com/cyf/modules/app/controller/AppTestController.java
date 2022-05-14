/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.cyf.modules.app.controller;


import com.cyf.modules.app.entity.UserEntity;
import com.cyf.modules.sys.service.SysUserTokenService;
import com.cyf.common.utils.R;
import com.cyf.modules.app.annotation.Login;
import com.cyf.modules.app.annotation.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * APP测试接口
 *
 
 */
@RestController
@RequestMapping("/app")
@Api(tags="APP测试接口")
public class AppTestController {


    @Autowired
    SysUserTokenService sysUserTokenService;
    @Login
    @GetMapping("userInfo")
    @ApiOperation("获取用户信息")
    public R userInfo(@LoginUser UserEntity user){

        if(StringUtils.isBlank(user.getAvatar())){
            user.setAvatar("https://s1.ax1x.com/2022/05/01/OCCr4J.jpg");
        }
        return R.ok().put("user", user);
    }

    @Login
    @GetMapping("userId")
    @ApiOperation("获取用户ID")
    public R userInfo(@RequestAttribute("userId") Integer userId){
        return R.ok().put("userId", userId);
    }

    @GetMapping("notToken")
    @ApiOperation("忽略Token验证测试")
    public R notToken(){
        return R.ok().put("msg", "无需token也能访问。。。");
    }

    /**
     * 退出
     */
    @GetMapping("/sys/logout/{userId}")
    public R logout(@PathVariable("userId") Long userId) {
        sysUserTokenService.logout(userId);
        return R.ok();
    }
}
