package io.cyf.modules.app.controller;

import java.util.*;
import java.util.stream.Collectors;

import io.cyf.modules.app.Dto.OrderInfoDto;
import io.cyf.modules.app.Dto.UserAddressDto;
import io.cyf.modules.app.Dto.UserAddressLocationDto;
import io.cyf.modules.app.annotation.Login;
import io.cyf.modules.app.annotation.LoginUser;
import io.cyf.modules.app.entity.OrderEntity;
import io.cyf.modules.sys.controller.AbstractController;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.cyf.modules.app.entity.UserEntity;
import io.cyf.modules.app.service.UserService;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.R;



/**
 * 用户表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
@RestController
@RequestMapping("app/user")
public class UserController  extends AbstractController {
    @Autowired
    private UserService userService;


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
