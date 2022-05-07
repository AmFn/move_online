package io.cyf.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cyf.common.utils.PageUtils;
import io.cyf.modules.app.Dto.*;
import io.cyf.modules.app.entity.EmployeeEntity;
import io.cyf.modules.app.entity.ExtraServiceEntity;
import io.cyf.modules.app.entity.OrderEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 订单表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:15
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<EmployeeEntity> getOrderAllEmployee(Long orderId);

    List<ExtraServiceEntity> getOrderAllService(Long orderId);

    boolean create(CreateOrderDto createOrderDto);

    PriceItem computePrice(OrderEntity order);

    BigDecimal compute(OrderEntity order);

    OrderInfoDto getInfoById(Long id);

   

    void confirm(Long id);

    void editPrice(EditPriceDto editPriceDto);

    void assignEmployee(AssignEmployeeDto assignEmployeeDto);

    void payed(Long id);

    void complete(Long id);

    void startMove(Long id);
}

