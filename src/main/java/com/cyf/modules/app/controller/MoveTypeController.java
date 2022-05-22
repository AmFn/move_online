package com.cyf.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import com.cyf.modules.app.entity.MoveTypeEntity;
import com.cyf.modules.app.service.MoveTypeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cyf.common.utils.PageUtils;
import com.cyf.common.utils.R;



/**
 * 搬家类型
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
@RestController
@RequestMapping("app/movetype")
@Api(tags="类型管理")
public class MoveTypeController {
    @Autowired
    private MoveTypeService moveTypeService;

    /**
     * 列表
     */
    @GetMapping("/list")
//    @RequiresPermissions("app:movetype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = moveTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
//    @RequiresPermissions("app:movetype:info")
    public R info(@PathVariable("id") Long id){
		MoveTypeEntity moveType = moveTypeService.getById(id);

        return R.ok().put("moveType", moveType);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("app:movetype:save")
    public R save(@RequestBody MoveTypeEntity moveType){
		moveTypeService.save(moveType);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
//    @RequiresPermissions("app:movetype:update")
    public R update(@RequestBody MoveTypeEntity moveType){
		moveTypeService.updateById(moveType);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
//    @RequiresPermissions("app:movetype:delete")
    public R delete(@RequestBody Long[] ids){
		moveTypeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
