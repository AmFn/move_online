/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */
package io.cyf.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cyf.common.exception.RRException;
import io.cyf.common.utils.PageCovertUtil;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.Query;
import io.cyf.common.validator.Assert;
import io.cyf.modules.app.Dto.OrderInfoDto;
import io.cyf.modules.app.Dto.UserAddressDto;
import io.cyf.modules.app.Dto.UserAddressLocationDto;
import io.cyf.modules.app.dao.UserDao;
import io.cyf.modules.app.entity.AddressEntity;
import io.cyf.modules.app.entity.OrderEntity;
import io.cyf.modules.app.entity.UserEntity;
import io.cyf.modules.app.form.LoginForm;
import io.cyf.modules.app.service.AddressService;
import io.cyf.modules.app.service.OrderService;
import io.cyf.modules.app.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service("userService")
class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
    @Autowired
    AddressService addressService;
    @Autowired
    OrderService orderService;
    @Override
    public UserEntity queryByMobile(String mobile) {
        return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("phone", mobile));
    }

    @Override
    public long login(LoginForm form) {
        UserEntity user = queryByMobile(form.getMobile());
        Assert.isNull(user, "手机号或密码错误");
        //密码错误
        if (!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))) {
            throw new RRException("手机号或密码错误");
        }
        return user.getId();
    }



    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<UserEntity>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((w) -> {
                w.or().like(UserEntity::getRealName, key)
                        .or().like(UserEntity::getPhone, key)
                        .or().like(UserEntity::getUsername, key)
                ;
            });
        }
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

    @Override
    public boolean updateAddr(Long oldAddrId, Long newAddrId, Long uid) {
        UserEntity userEntity = baseMapper.selectById(uid);
        userEntity.setNewAddressId(newAddrId);
        userEntity.setAddressId(oldAddrId);
        int id = baseMapper.updateById(userEntity);
        return id > 0;
    }

    @Override
    public UserEntity getUserInfo() {
        return null;
    }

    @Override
    public void disableUser(Long id) {
        UserEntity userEntity = baseMapper.selectById(id);
        if(userEntity!=null){
            userEntity.setEnable(0);
            baseMapper.updateById(userEntity);
            return;
        }
        throw new  RRException("用户不存在");

    }

    @Override
    public void enableUser(Long id) {
        UserEntity userEntity = baseMapper.selectById(id);
        if(userEntity!=null){
            userEntity.setEnable(1);
            baseMapper.updateById(userEntity);
            return;
        }
        throw new  RRException("用户不存在");
    }

    @Override
    public UserEntity getByAddressId(Long id) {
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getAddressId,id)
                .or().eq(UserEntity::getNewAddressId,id);
        return baseMapper.selectOne(wrapper);

    }

    @Override
    public PageUtils getAddressDtoPage(Map<String, Object> params) {
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<UserEntity>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((w) -> {
                w.or().like(UserEntity::getRealName, key)
                ;
            });
        }
        wrapper.isNotNull(UserEntity::getNewAddressId);
        wrapper.isNotNull(UserEntity::getAddressId);
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                wrapper
        );
        List<UserEntity> records = page.getRecords();
        List<UserAddressDto> result= new ArrayList<>();
        records.forEach(userEntity -> {
            UserAddressDto dto = new UserAddressDto();
                dto.setRealName(userEntity.getRealName());
                dto.setUserId(userEntity.getId());
                dto.setPhone(userEntity.getPhone());
            Long addressId = userEntity.getAddressId();
            Long newAddressId = userEntity.getNewAddressId();
            AddressEntity nowAddr = addressService.getById(addressId);
            AddressEntity newAddr = addressService.getById(newAddressId);

            Optional.ofNullable(nowAddr).ifPresent(addr->{
                dto.setNowAddress(addr.getDetail());
                dto.setNowLocation(addr.getLocation());
            });
            Optional.ofNullable(newAddr).ifPresent(addr->{
                dto.setNewAddress(addr.getDetail());
                dto.setNewLocation(addr.getLocation());
            });

            result.add(dto);

        });
        IPage<UserAddressDto> userAddressDtoIPage = PageCovertUtil.pageVoCovert(page, UserAddressDto.class);
        userAddressDtoIPage.setRecords(result);
        return new PageUtils(userAddressDtoIPage);
    }

    @Override
    public UserAddressDto getAddressInfo(Long uid) {
        UserAddressDto dto = new UserAddressDto();
        UserEntity userEntity = baseMapper.selectById(uid);
        if(userEntity!=null){
            dto.setRealName(userEntity.getRealName());
            dto.setUserId(userEntity.getId());
            dto.setPhone(userEntity.getPhone());
            Long addressId = userEntity.getAddressId();
            Long newAddressId = userEntity.getNewAddressId();
            AddressEntity nowAddr = addressService.getById(addressId);
            AddressEntity newAddr = addressService.getById(newAddressId);

            Optional.ofNullable(nowAddr).ifPresent(addr->{
                dto.setNowAddress(addr.getDetail());
                dto.setNowLocation(addr.getLocation());
            });
            Optional.ofNullable(newAddr).ifPresent(addr->{
                dto.setNewAddress(addr.getDetail());
                dto.setNewLocation(addr.getLocation());
            });
        }



        return  dto;
    }

    @Override
    public void updateLocation(UserAddressLocationDto locationDto) {
        if(locationDto.getId()==null){
            throw  new  RRException("用户id为空");
        }
        UserEntity userEntity = baseMapper.selectById(locationDto.getId());
        if(userEntity==null){
            throw  new  RRException("用户不存在");
        }
        Long newAddressId = userEntity.getNewAddressId();
        Long nowAddressId = userEntity.getAddressId();
        if(!StringUtils.isEmpty(locationDto.getNowLocation())){
            AddressEntity addressEntity = addressService.getById(nowAddressId);
            addressEntity.setLocation(locationDto.getNowLocation());
            addressService.updateById(addressEntity);
        }

        if(!StringUtils.isEmpty(locationDto.getNewLocation())){
            AddressEntity addressEntity = addressService.getById(newAddressId);
            addressEntity.setLocation(locationDto.getNewLocation());
            addressService.updateById(addressEntity);
        }
    }

    @Override
    public List<OrderInfoDto> getOrders(Long id) {
        List<OrderInfoDto> result = new ArrayList<>();
        LambdaQueryWrapper<OrderEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderEntity::getUserId,id);
        List<OrderEntity> list = orderService.list(wrapper);
        list.forEach(orderEntity -> {
            OrderInfoDto infoById = orderService.getInfoById(orderEntity.getId());
            result.add(infoById);
        });

        return result;
    }
}
