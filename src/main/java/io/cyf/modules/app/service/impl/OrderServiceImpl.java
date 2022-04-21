package io.cyf.modules.app.service.impl;

import io.cyf.modules.app.Dto.CreateOrderDto;
import io.cyf.modules.app.service.AddressService;
import io.cyf.modules.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.Query;

import io.cyf.modules.app.dao.OrderDao;
import io.cyf.modules.app.entity.OrderEntity;
import io.cyf.modules.app.service.OrderService;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    AddressService addressService;
    @Autowired
    UserService userService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    public void  createOrder(CreateOrderDto createOrderDto){

    }
}