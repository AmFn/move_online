package io.cyf.modules.app.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.cyf.common.exception.RRException;
import io.cyf.modules.app.Dto.OrderInfoDto;
import io.cyf.modules.app.Dto.UserAddressDto;
import io.cyf.modules.app.Dto.UserAddressLocationDto;
import io.cyf.modules.app.annotation.Login;
import io.cyf.modules.app.annotation.LoginUser;
import io.cyf.modules.app.entity.OrderEntity;
import io.cyf.modules.app.form.RegisterForm;
import io.cyf.modules.app.service.SmsService;
import io.cyf.modules.sys.controller.AbstractController;
import io.cyf.modules.sys.service.SysCaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.cyf.modules.app.entity.UserEntity;
import io.cyf.modules.app.service.UserService;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.R;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
@RestController
@RequestMapping("app/user")
@Api(tags="用户接口")
public class UserController  extends AbstractController {
    @Autowired
    private UserService userService;
    @Autowired
    private SysCaptchaService sysCaptchaService;
@Autowired
SmsService smsService;
    @Login
    @GetMapping("userInfo")
    @ApiOperation("获取用户信息")
    public R userInfo(@LoginUser UserEntity user){
        userService.getUserInfo();
        return R.ok().put("user", user);
    }


    @GetMapping("/user_address_list")

    public R userAddressList(@RequestParam Map<String, Object> params){
        PageUtils page = userService.getAddressDtoPage(params);

        return R.ok().put("page", page);
    }


    @PostMapping("/reset_pwd")
    public R resetPassword(@RequestBody RegisterForm form){
        UserEntity one = userService.getOne(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getPhone, form.getPhone()));
        if(one==null){
            throw new RRException("手机号不存在");
        }

        boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
        if(!captcha){
            return R.error("验证码不正确");
        }
        one.setPassword(DigestUtils.sha256Hex(form.getPassword()));
        userService.updateById(one);
        return R.ok();
    }

    /**
     * 列表
     */
    @GetMapping("/list")
//    @RequiresPermissions("app:user:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }

    @GetMapping("/orders/{id}")

    public R orders(@PathVariable("id") Long id){
       List<OrderInfoDto> list  = userService.getOrders(id);

        return R.ok().put("list", list);
    }
    /**
     * 信息
     */
    @GetMapping("/info/{id}")
//    @RequiresPermissions("app:user:info")
    public R info(@PathVariable("id") Long id){
		UserEntity user = userService.getById(id);

        return R.ok().put("user", user);
    }


    /**
     * 信息
     */
    @GetMapping("/address_info/{id}")

    public R addressInfo(@PathVariable("id") Long id){
        UserAddressDto dto = userService.getAddressInfo(id);

        return R.ok().put("user", dto);
    }


    @PostMapping("/update_address_location")
    public R save(@RequestBody UserAddressLocationDto locationDto){
        userService.updateLocation(locationDto);

        return R.ok();
    }


    @GetMapping("/code/{phone}")
    public R getCode(@PathVariable("phone") String phone){
        Map<String, Object> map = smsService.sendMsg(phone);
        boolean flag = (boolean) map.get("flag");
        if (flag){
            return R.ok().put("data", map.get("uuid"));
        }
        else {
            return R.error("短信发送失败");
        }
    }
    @PostMapping("/update")
//    @RequiresPermissions("app:user:save")
    public R update(@RequestBody UserEntity user){
        if(user.getId()==null){
            throw  new RRException("用户信息不存在");
        }
        UserEntity userEntity = userService.getById(user.getId());
        if(user.getAvatar()!=null ){
            userEntity.setAvatar(user.getAvatar());
        }
        userEntity.setPhone(user.getPhone());
        userEntity.setUsername(user.getUsername());
        userEntity.setRealName(user.getRealName());
        userEntity.setAge(user.getAge());
        userService.updateById(userEntity);
        UserEntity result = userService.getById(user.getId());
        return R.ok().put("user",result);
    }
    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("app:user:save")
    public R save(@RequestBody UserEntity user){
		userService.save(user);

        return R.ok();
    }

    /**
     * 禁用
     */
    @GetMapping("/disable/{id}")
    public R disable(@PathVariable("id") Long id){
		userService.disableUser(id);

        return R.ok();
    }


    /**
     * 启用
     */
    @GetMapping("/enable/{id}")
    public R enable(@PathVariable("id") Long id){
        userService.enableUser(id);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
//    @RequiresPermissions("app:user:delete")
    public R delete(@RequestBody Long[] ids){
		userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }



}
