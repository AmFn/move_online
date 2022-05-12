package io.cyf.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
@Api(tags="车辆管理接口")
public class TruckController {
    @Autowired
    private TruckService truckService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = truckService.queryPage(params);

        return R.ok().put("page", page);
    }
    @GetMapping("/info/{id}/{orderId}")
    public R distribution(@PathVariable("id") Long id,@PathVariable("orderId") Long orderId){
        truckService.distribution(id,orderId);
        return R.ok();
    }
    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		TruckEntity truck = truckService.getById(id);

        return R.ok().put("truck", truck);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TruckEntity truck){
        truck.setStatus(1);
		truckService.save(truck);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody TruckEntity truck){
		truckService.updateById(truck);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")

    public R delete(@RequestBody Long[] ids){
		truckService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
