package io.cyf.modules.app.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import io.cyf.common.exception.RRException;
import io.cyf.modules.app.Dto.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.cyf.modules.app.entity.OrderEntity;
import io.cyf.modules.app.service.OrderService;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.R;



/**
 * 订单表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:15
 */
@RestController
@RequestMapping("app/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 列表
     */
    /**
     * 创建订单
     */
    @PostMapping("/create")
    @Validated
    public R list(@RequestBody CreateOrderDto createOrderDto){
        boolean result = orderService.create(createOrderDto);

        return R.ok().put("page", result);
    }



    @GetMapping("/list")
//    @RequiresPermissions("app:order:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderService.queryPage(params);

        return R.ok().put("page", page);
    }


    @GetMapping("/price_info/{id}")

    public R priceInfo(@PathVariable("id") Long id){
        OrderEntity order = orderService.getById(id);
        if(order==null){
            throw  new RRException("未查询到订单");
        }
        PriceItem priceItem = orderService.computePrice(order);

        return R.ok().put("order",priceItem );

    }
    @GetMapping("/confirm/{id}")
    public R  confirm(@PathVariable("id") Long id){
        orderService.confirm(id);
        return  R.ok();
    }


    @GetMapping("/complete/{id}")
    public R  complete(@PathVariable("id") Long id){
        orderService.complete(id);
        return  R.ok();
    }
    @GetMapping("/start_move/{id}")
    public R  startMove(@PathVariable("id") Long id){
        orderService.startMove(id);
        return  R.ok();
    }
    @GetMapping("/payed/{id}")
    public R  payed(@PathVariable("id") Long id){
        orderService.payed(id);
        return  R.ok();
    }
    @PostMapping("/editPrice")
    public R  editPrice(@RequestBody EditPriceDto editPriceDto){
        orderService.editPrice(editPriceDto);
        return  R.ok();
    }
    /**
     * 信息
     */
    @GetMapping("/info/{id}")
//    @RequiresPermissions("app:order:info")
    public R info(@PathVariable("id") Long id){
        OrderInfoDto order = orderService.getInfoById(id);
        return R.ok().put("order",order );
    }
@PostMapping("/assign_emp")
    public R assign(@RequestBody AssignEmployeeDto assignEmployeeDto){
        orderService.assignEmployee(assignEmployeeDto);
        return  R.ok();
}
    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("app:order:save")
    public R save(@RequestBody OrderEntity order){
		orderService.save(order);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
//    @RequiresPermissions("app:order:update")
    public R update(@RequestBody OrderEntity order){
		orderService.updateById(order);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
//    @RequiresPermissions("app:order:delete")
    public R delete(@RequestBody Long[] ids){
		orderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
