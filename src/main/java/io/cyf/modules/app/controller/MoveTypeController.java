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

import io.cyf.modules.app.entity.MoveTypeEntity;
import io.cyf.modules.app.service.MoveTypeService;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.R;



/**
 * 搬家类型
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
@RestController
@RequestMapping("app/movetype")
public class MoveTypeController {
    @Autowired
    private MoveTypeService moveTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("app:movetype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = moveTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("app:movetype:info")
    public R info(@PathVariable("id") Long id){
		MoveTypeEntity moveType = moveTypeService.getById(id);

        return R.ok().put("moveType", moveType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("app:movetype:save")
    public R save(@RequestBody MoveTypeEntity moveType){
		moveTypeService.save(moveType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("app:movetype:update")
    public R update(@RequestBody MoveTypeEntity moveType){
		moveTypeService.updateById(moveType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("app:movetype:delete")
    public R delete(@RequestBody Long[] ids){
		moveTypeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
