package io.cyf.modules.app.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.cyf.common.exception.RRException;
import io.cyf.common.utils.Constant;
import io.cyf.common.utils.PageCovertUtil;
import io.cyf.common.validator.ValidatorUtils;
import io.cyf.modules.app.Dto.CreateOrderDto;
import io.cyf.modules.app.Dto.OrderInfoDto;
import io.cyf.modules.app.entity.*;
import io.cyf.modules.app.service.*;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Constants;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.Query;

import io.cyf.modules.app.dao.OrderDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;

@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    AddressService addressService;
    @Autowired
    UserService userService;
    @Autowired
    ServiceOrderService serviceOrderService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    MoveTypeService moveTypeService;
    @Autowired
    TruckService truckService;
    @Autowired
    EmployeeOrderService employeeOrderService;

    @Autowired
    ExtraServiceService extraServiceService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<OrderEntity> wrapper = new LambdaQueryWrapper<OrderEntity>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {

        }
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                wrapper
        );
        List<OrderInfoDto> res = new ArrayList<>();
        page.getRecords().forEach(order->{
            OrderInfoDto orderInfoDto = covertOrderInfoDto(order);
            res.add(orderInfoDto);
        });
        IPage<OrderInfoDto> orderInfoDtoIPage = PageCovertUtil.pageVoCovert(page, OrderInfoDto.class);
        orderInfoDtoIPage.setRecords(res);
        return new PageUtils(orderInfoDtoIPage);
    }

    public OrderInfoDto covertOrderInfoDto(OrderEntity order){
        OrderInfoDto dto = new OrderInfoDto();
        List<ExtraServiceEntity> allService = getOrderAllService(order.getId());
        List<EmployeeEntity> allEmployee = getOrderAllEmployee(order.getId());
        MoveTypeEntity moveType = moveTypeService.getById(order.getMoveTypeId());
        if(order.getTruckId()!=null){
            TruckEntity truck = truckService.getById(order.getTruckId());
            dto.setTruckEntity(truck);
        }


        AddressEntity source = addressService.getById(order.getSourceAddressId());
        AddressEntity destination = addressService.getById(order.getDestinationAddressId());
        UserEntity user = userService.getById(order.getUserId());
        BeanUtils.copyProperties(order,dto);

        dto.setExtraServices(allService);
        dto.setEmployeeEntities(allEmployee);
        dto.setMoveTypeEntity(moveType);

        dto.setSourceAddressEntity(source);
        dto.setDestinationAddressEntity(destination);
        dto.setUserEntity(user);

        return dto;
    }

    @Override
    public List<EmployeeEntity> getOrderAllEmployee(Long orderId){
        List<Long> employeeIds = employeeOrderService.getBaseMapper().selectList(new LambdaQueryWrapper<EmployeeOrderEntity>().eq(EmployeeOrderEntity::getOrderId, orderId)).stream().map(EmployeeOrderEntity::getEmployeeId).collect(Collectors.toList());
        if(CollectionUtil.isEmpty(employeeIds)){
            return  new ArrayList<>();
        }
        return employeeService.listByIds(employeeIds);
    }


    @Override
    public List<ExtraServiceEntity> getOrderAllService(Long orderId){
        List<Long> serviceIds = serviceOrderService.getBaseMapper().selectList(new LambdaQueryWrapper<ServiceOrderEntity>().eq(ServiceOrderEntity::getOrderId, orderId)).stream().map(ServiceOrderEntity::getServiceId).collect(Collectors.toList());
        if(CollectionUtil.isEmpty(serviceIds)){
            return  new ArrayList<>();
        }
        return extraServiceService.listByIds(serviceIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create(CreateOrderDto createOrderDto) {
        //验证参数
        ValidatorUtils.validateEntity(createOrderDto);
        ValidatorUtils.validateEntity(createOrderDto.getNowAddress());
        ValidatorUtils.validateEntity(createOrderDto.getNewAddress());
        //检查用户是否被禁用
        boolean available = checkUserAvailable(createOrderDto.getUid());
        if(!available){
            throw new RRException("用户已被禁用");
        }
        //保存用户地址
        boolean saveAddress = addressService.saveAddress(createOrderDto.getNowAddress(), createOrderDto.getNewAddress(), createOrderDto.getUid());
        if(!saveAddress){
            throw  new RRException("地址保存失败");

        }
        //创建订单
        long orderId = createOrderByParam(createOrderDto);
        if(orderId<0){
            throw  new RRException("价格查询失败");
        }
        //添加额外服务
        if(CollectionUtil.isNotEmpty(createOrderDto.getExtraServiceIds())){
            createOrderDto.getExtraServiceIds().forEach(serviceId->{
                ServiceOrderEntity serviceOrderEntity = new ServiceOrderEntity();
                serviceOrderEntity.setOrderId(orderId);
                serviceOrderEntity.setServiceId(serviceId);
                serviceOrderService.getBaseMapper().insert(serviceOrderEntity);
            });
        }
        return true;
    }

    private boolean checkUserAvailable(Long uid) {
        int enable = userService.getById(uid).getEnable();
        return enable==1;


    }

    private long createOrderByParam(CreateOrderDto createOrderDto) {
        UserEntity user = userService.getById(createOrderDto.getUid());

        OrderEntity order = new OrderEntity();
        order.setUserId(createOrderDto.getUid());
        order.setDestinationLocation(createOrderDto.getNewAddress().getLocation());
        order.setSourceLocation(createOrderDto.getNowAddress().getLocation());
        String drivingDistance = addressService.getDrivingDistance(createOrderDto.getNowAddress().getLocation(), createOrderDto.getNewAddress().getLocation());
        order.setDistance(BigDecimal.valueOf(Long.parseLong(drivingDistance)));
        order.setStatus(0);
        order.setMoveTypeId(createOrderDto.getMoveTypeId());
        order.setMoveTime(createOrderDto.getMoveTime());
        order.setStorey(createOrderDto.getStorey());
        order.setIsDel(0);
        order.setExtraFee(BigDecimal.ZERO);
        order.setCreateTime(new Date());
        order.setSourceAddressId(user.getAddressId());
        order.setDestinationAddressId(user.getNewAddressId());
        int insert = baseMapper.insert(order);
        if(insert<0){
            throw new RRException("操作失败");
        }
        return order.getId();
    }

    @Override
    public   BigDecimal compute(OrderEntity order){

        BigDecimal total =  BigDecimal.ZERO;
        //距离费用
        BigDecimal distance = order.getDistance();
        BigDecimal distanceTotal = distance.multiply(new BigDecimal(Constant.DISTANCE_FEE));;
        if(distance.longValue()>Constant.DISTANCE_TEN_KILO_MITRE){
            distanceTotal =distanceTotal.add(new BigDecimal(distance.longValue()-Constant.DISTANCE_TEN_KILO_MITRE).multiply(new BigDecimal(Constant.DISTANCE_FEE_EXTRA)));
        }else{

        }
        total = total.add(distanceTotal);
        //楼层费用
        if(order.getStorey()>0){
        total = total.add(new BigDecimal(order.getStorey()).multiply(new BigDecimal(Constant.STOREY_FEE)));
        }
        //类型基础费用
        Long moveTypeId = order.getMoveTypeId();
        MoveTypeEntity moveType = moveTypeService.getById(moveTypeId);
        total = total.add(moveType.getPrice());
        //服务费用
        List<Long> serviceIds = serviceOrderService
                .getBaseMapper()
                .selectList(new LambdaQueryWrapper<ServiceOrderEntity>()
                        .eq(ServiceOrderEntity::getOrderId, order.getId()))
                .stream()
                .map(ServiceOrderEntity::getServiceId)
                .collect(Collectors.toList());
        List<BigDecimal> serviceFeeList = extraServiceService
                .listByIds(serviceIds)
                .stream()
                .map(ExtraServiceEntity::getPrice)
                .collect(Collectors.toList());
        for (BigDecimal fee : serviceFeeList) {
          total =  total.add(fee);
        }
        return  total;
    }

    @Override
    public OrderInfoDto getInfoById(Long id) {
        OrderEntity orderEntity = baseMapper.selectById(id);
        OrderInfoDto orderInfoDto = covertOrderInfoDto(orderEntity);
        return orderInfoDto;

    }
}