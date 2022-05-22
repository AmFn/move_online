package com.cyf.modules.app.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.cyf.modules.app.entity.AddressEntity;
import com.cyf.modules.app.service.AddressService;
import com.cyf.modules.app.Dto.SearchAddressResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cyf.common.utils.PageUtils;
import com.cyf.common.utils.R;



/**
 * 用户地址表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
@RestController
@RequestMapping("app/address")
@Api(tags = "地址管理")
public class AddressController {
    @Autowired
    private AddressService addressService;


    @GetMapping("/{keyword}")
    @ApiOperation(value = "搜索地址", httpMethod = "GET",notes = "搜索地址")
    public R addAddress(@PathVariable String keyword){
        List<SearchAddressResponseDto> list = addressService.searchByKeyWord(keyword,null,10,1);
//        List<SearchAddressResponseDto> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            SearchAddressResponseDto dto = new SearchAddressResponseDto();
//            dto.setAddress("贵州省贵阳市贵州大学北校区");
//            dto.setLocation("100.100.000");
//            list.add(dto);
//        }
        return R.ok().put("data", list);
    }



    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "地址列表", httpMethod = "GET")
//    @RequiresPermissions("app:address:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = addressService.queryPage(params);

        return R.ok().put("page", page);
    }

//    @GetMapping("/user_address_list")
//
//    public R userAddressList(@RequestParam Map<String, Object> params){
//        PageUtils page = addressService.getAddressDtoPage(params);
//        return R.ok().put("page", page);
//
//    }
    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation(value = "地址详情", httpMethod = "GET")
//    @RequiresPermissions("app:address:info")
    public R info(@PathVariable("id") Long id){
		AddressEntity address = addressService.getById(id);

        return R.ok().put("address", address);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "添加地址", httpMethod = "POST")
    public R save(@RequestBody AddressEntity address){
		addressService.save(address);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改地址", httpMethod = "POST")
//    @RequiresPermissions("app:address:update")
    public R update(@RequestBody AddressEntity address){
		addressService.updateById(address);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
//    @RequiresPermissions("app:address:delete")
    @ApiOperation(value = "删除地址", httpMethod = "DELETE")
    public R delete(@RequestBody Long[] ids){
		addressService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
