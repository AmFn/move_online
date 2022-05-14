package com.cyf.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import com.cyf.modules.app.entity.CompensateEntity;
import com.cyf.modules.app.service.CompensateService;
import com.cyf.modules.app.Dto.CompensateCreateDto;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cyf.common.utils.PageUtils;
import com.cyf.common.utils.R;



/**
 * 赔偿物品表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
@RestController
@RequestMapping("app/compensate")
@Api(tags="赔偿管理")
public class CompensateController {
    @Autowired
    private CompensateService compensateService;

    /**
     * 列表
     */
    @GetMapping("/list")
//    @RequiresPermissions("app:compensate:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = compensateService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
//    @RequiresPermissions("app:compensate:info")
    public R info(@PathVariable("id") Long id){
		CompensateEntity compensate = compensateService.getById(id);

        return R.ok().put("compensate", compensate);
    }

    @GetMapping("/audit/{id}")
//    @RequiresPermissions("app:compensate:info")
    public R audit(@PathVariable("id") Long id){
        CompensateEntity compensate = compensateService.getById(id);
        compensate.setIsAudit(compensate.getIsAudit()==0?1:0);
        compensateService.updateById(compensate);

        return R.ok();
    }
    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("app:compensate:save")
    public R save(@RequestBody CompensateEntity compensate){
		compensateService.save(compensate);

        return R.ok();
    }


    @PostMapping("/compensate_create")

    public R compensateCreate(@RequestBody CompensateCreateDto compensate){
        compensateService.saveCompensate(compensate);

        return R.ok();
    }
    /**
     * 修改
     */
    @PostMapping("/update")
//    @RequiresPermissions("app:compensate:update")
    public R update(@RequestBody CompensateEntity compensate){
		compensateService.updateById(compensate);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
//    @RequiresPermissions("app:compensate:delete")
    public R delete(@RequestBody Long[] ids){
		compensateService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
