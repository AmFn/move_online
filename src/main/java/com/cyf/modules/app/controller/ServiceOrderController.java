package com.cyf.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import com.cyf.modules.app.entity.ServiceOrderEntity;
import com.cyf.modules.app.service.ServiceOrderService;
import com.cyf.common.utils.PageUtils;
import com.cyf.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务_订单表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-04-21 17:21:02
 */
@RestController
@RequestMapping("app/serviceorder")
public class ServiceOrderController {
    @Autowired
    private ServiceOrderService serviceOrderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:serviceorder:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = serviceOrderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:serviceorder:info")
    public R info(@PathVariable("id") Long id){
		ServiceOrderEntity serviceOrder = serviceOrderService.getById(id);

        return R.ok().put("serviceOrder", serviceOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:serviceorder:save")
    public R save(@RequestBody ServiceOrderEntity serviceOrder){
		serviceOrderService.save(serviceOrder);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:serviceorder:update")
    public R update(@RequestBody ServiceOrderEntity serviceOrder){
		serviceOrderService.updateById(serviceOrder);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:serviceorder:delete")
    public R delete(@RequestBody Long[] ids){
		serviceOrderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
