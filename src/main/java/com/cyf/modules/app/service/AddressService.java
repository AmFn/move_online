package com.cyf.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyf.common.utils.PageUtils;
import com.cyf.modules.app.Dto.SaveAddressDTO;
import com.cyf.modules.app.Dto.SearchAddressResponseDto;
import com.cyf.modules.app.entity.AddressEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户地址表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
public interface AddressService extends IService<AddressEntity> {
    boolean saveAddress(SaveAddressDTO oldAddress, SaveAddressDTO newAddress, Long uid);
    PageUtils queryPage(Map<String, Object> params);

    String getDrivingDistance(String origin, String destination);

    List<SearchAddressResponseDto> searchByKeyWord(String keyword, String city);


}

