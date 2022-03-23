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

import io.cyf.modules.app.entity.CompensateEntity;
import io.cyf.modules.app.service.CompensateService;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.R;



/**
 * 赔偿物品表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
@RestController
@RequestMapping("app/compensate")
public class CompensateController {
    @Autowired
    private CompensateService compensateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("app:compensate:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = compensateService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("app:compensate:info")
    public R info(@PathVariable("id") Long id){
		CompensateEntity compensate = compensateService.getById(id);

        return R.ok().put("compensate", compensate);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("app:compensate:save")
    public R save(@RequestBody CompensateEntity compensate){
		compensateService.save(compensate);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("app:compensate:update")
    public R update(@RequestBody CompensateEntity compensate){
		compensateService.updateById(compensate);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("app:compensate:delete")
    public R delete(@RequestBody Long[] ids){
		compensateService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
