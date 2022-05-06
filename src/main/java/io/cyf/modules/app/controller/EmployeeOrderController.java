package io.cyf.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cyf.modules.app.entity.EmployeeOrderEntity;
import io.cyf.modules.app.service.EmployeeOrderService;



/**
 * 员工_订单表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-04-21 17:21:02
 */
@RestController
@RequestMapping("app/employeeorder")
public class EmployeeOrderController {
    @Autowired
    private EmployeeOrderService employeeOrderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:employeeorder:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = employeeOrderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:employeeorder:info")
    public R info(@PathVariable("id") Long id){
		EmployeeOrderEntity employeeOrder = employeeOrderService.getById(id);

        return R.ok().put("employeeOrder", employeeOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:employeeorder:save")
    public R save(@RequestBody EmployeeOrderEntity employeeOrder){


		employeeOrderService.save(employeeOrder);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:employeeorder:update")
    public R update(@RequestBody EmployeeOrderEntity employeeOrder){
		employeeOrderService.updateById(employeeOrder);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:employeeorder:delete")
    public R delete(@RequestBody Long[] ids){
		employeeOrderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
