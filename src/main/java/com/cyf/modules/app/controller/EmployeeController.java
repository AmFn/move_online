package com.cyf.modules.app.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.cyf.common.utils.ExcelUtil;
import com.cyf.modules.app.Dto.EmpTemplate;
import com.cyf.modules.app.entity.EmployeeEntity;
import com.cyf.modules.app.service.EmployeeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cyf.common.utils.PageUtils;
import com.cyf.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 员工表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
@RestController
@RequestMapping("app/employee")
@Api(tags="员工管理")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 列表
     */
    @GetMapping("/list")
//    @RequiresPermissions("app:employee:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = employeeService.queryPage(params);

        return R.ok().put("page", page);
    }


    @GetMapping("/idle_emp")

    public R list(){
        List<EmployeeEntity> employeeEntities = employeeService.getIdleEmployee();

        return R.ok().put("list", employeeEntities);
    }

    @GetMapping("/drivers")

    public R drivers(){
        List<EmployeeEntity> list = employeeService.getDrivers();

        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
//    @RequiresPermissions("app:employee:info")
    public R info(@PathVariable("id") Long id){
		EmployeeEntity employee = employeeService.getById(id);

        return R.ok().put("employee", employee);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("app:employee:save")
    public R save(@RequestBody EmployeeEntity employee){
        employee.setStatus(1);
        employee.setIsDel(0);
		employeeService.save(employee);

        return R.ok();
    }
    @CrossOrigin
    @PostMapping("/add_excel")
//    @RequiresPermissions("app:employee:save")
    public R addByExcel(@RequestBody MultipartFile file){
        employeeService.saveByExcel(file,employeeService);

        return R.ok();
    }

    @GetMapping("/excel/template")
    public void downloadTemplate(HttpServletResponse response) {
        String fileName = "导入用户模板";
        String sheetName = "导入用户模板";
        List<EmpTemplate> userList = new ArrayList<>();
        EmpTemplate template = EmpTemplate.builder().name("陈煜峰").phone("16685274469").type("1").build();
        userList.add(template);
        try {
            ExcelUtil.writeExcel(response, userList, fileName, sheetName, EmpTemplate.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 修改
     */
    @PostMapping("/update")
//    @RequiresPermissions("app:employee:update")
    public R update(@RequestBody EmployeeEntity employee){
		employeeService.updateById(employee);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
//    @RequiresPermissions("app:employee:delete")
    public R delete(@RequestBody Long[] ids){
		employeeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
