package io.cyf.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cyf.modules.app.entity.EmployeeEntity;
import io.cyf.modules.app.service.EmployeeService;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.R;



/**
 * 员工表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
@RestController
@RequestMapping("app/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("app:employee:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = employeeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("app:employee:info")
    public R info(@PathVariable("id") Long id){
		EmployeeEntity employee = employeeService.getById(id);

        return R.ok().put("employee", employee);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("app:employee:save")
    public R save(@RequestBody EmployeeEntity employee){
		employeeService.save(employee);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("app:employee:update")
    public R update(@RequestBody EmployeeEntity employee){
		employeeService.updateById(employee);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("app:employee:delete")
    public R delete(@RequestBody Long[] ids){
		employeeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
