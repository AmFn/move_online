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

import io.cyf.modules.app.entity.TruckEntity;
import io.cyf.modules.app.service.TruckService;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.R;



/**
 * 货车表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:15
 */
@RestController
@RequestMapping("app/truck")
public class TruckController {
    @Autowired
    private TruckService truckService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("app:truck:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = truckService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("app:truck:info")
    public R info(@PathVariable("id") Long id){
		TruckEntity truck = truckService.getById(id);

        return R.ok().put("truck", truck);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("app:truck:save")
    public R save(@RequestBody TruckEntity truck){
		truckService.save(truck);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("app:truck:update")
    public R update(@RequestBody TruckEntity truck){
		truckService.updateById(truck);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("app:truck:delete")
    public R delete(@RequestBody Long[] ids){
		truckService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
