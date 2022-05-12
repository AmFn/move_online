package io.cyf.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.cyf.modules.app.entity.ExtraServiceEntity;
import io.cyf.modules.app.service.ExtraServiceService;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.R;



/**
 * 额外服务表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
@RestController
@RequestMapping("app/extraservice")
@Api(tags="服务管理")
public class ExtraServiceController {
    @Autowired
    private ExtraServiceService extraServiceService;

    /**
     * 列表
     */
    @GetMapping("/list")
//    @RequiresPermissions("app:extraservice:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = extraServiceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
//    @RequiresPermissions("app:extraservice:info")
    public R info(@PathVariable("id") Long id){
		ExtraServiceEntity extraService = extraServiceService.getById(id);

        return R.ok().put("extraService", extraService);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("app:extraservice:save")
    public R save(@RequestBody ExtraServiceEntity extraService){
		extraServiceService.save(extraService);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
//    @RequiresPermissions("app:extraservice:update")
    public R update(@RequestBody ExtraServiceEntity extraService){
		extraServiceService.updateById(extraService);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
//    @RequiresPermissions("app:extraservice:delete")
    public R delete(@RequestBody Long[] ids){
		extraServiceService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
