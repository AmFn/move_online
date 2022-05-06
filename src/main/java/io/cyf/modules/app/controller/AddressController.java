package io.cyf.modules.app.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.cyf.common.utils.Query;
import io.cyf.modules.app.Dto.SearchAddressResponseDto;
import io.cyf.modules.app.entity.UserEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import io.cyf.modules.app.entity.AddressEntity;
import io.cyf.modules.app.service.AddressService;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.R;



/**
 * 用户地址表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
@RestController
@RequestMapping("app/address")
public class AddressController {
    @Autowired
    private AddressService addressService;


    @GetMapping("/{keyword}")

    public R addAddress(@PathVariable String keyword){
        List<SearchAddressResponseDto> list = addressService.searchByKeyWord(keyword,null);
//        List<SearchAddressResponseDto> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            SearchAddressResponseDto dto = new SearchAddressResponseDto();
//            dto.setAddress("贵州省贵阳市贵州大学北校区");
//            dto.setLocation("100.100.000");
//            list.add(dto);
//        }
        return R.ok().put("data", list);
    }


    @RequestMapping("/addAddress")
//    @RequiresPermissions("app:address:list")
    public R addAddress(@RequestParam Map<String, Object> params){
        PageUtils page = addressService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
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
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("app:address:info")
    public R info(@PathVariable("id") Long id){
		AddressEntity address = addressService.getById(id);

        return R.ok().put("address", address);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("app:address:save")
    public R save(@RequestBody AddressEntity address){
		addressService.save(address);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("app:address:update")
    public R update(@RequestBody AddressEntity address){
		addressService.updateById(address);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("app:address:delete")
    public R delete(@RequestBody Long[] ids){
		addressService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
