package com.cyf.modules.app.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.cyf.modules.app.Dto.*;
import com.cyf.modules.app.entity.*;
import com.cyf.modules.app.service.*;
import com.cyf.common.exception.RRException;
import com.cyf.common.utils.Constant;
import com.cyf.common.utils.PageCovertUtil;
import com.cyf.common.validator.ValidatorUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyf.common.utils.PageUtils;
import com.cyf.common.utils.Query;

import com.cyf.modules.app.dao.OrderDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
        String status = (String) params.get("status");
        if(!StringUtils.isEmpty(key)){

            List<Long> ids = userService.list(new LambdaQueryWrapper<UserEntity>().like(UserEntity::getRealName, key)).stream().map(UserEntity::getId).collect(Collectors.toList());
            wrapper.in(OrderEntity::getUserId,ids);
        }
        wrapper.eq(!StringUtils.isEmpty(status),OrderEntity::getStatus,status);
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

//        TruckEntity truckEntity = truckService.getBaseMapper().selectOne(new LambdaQueryWrapper<TruckEntity>().eq(TruckEntity::getStatus, 1));
//        order.setTruckId(truckEntity.getId());

        int insert = baseMapper.insert(order);
        if(insert<0){
            throw new RRException("操作失败");
        }

        //分配货车
        if(order.getId()!=null){
            TruckEntity truck = truckService.getOneByType(createOrderDto.getTruckType());
            truckService.distribution(truck.getId(),order.getId());
        }

        return order.getId();
    }

@Override
public PriceItem computePrice(OrderEntity order){
    PriceItem result = new PriceItem();
    BigDecimal total =  BigDecimal.ZERO;
    //距离费用
    BigDecimal distance = order.getDistance();
    BigDecimal distanceTotal = distance.multiply(new BigDecimal(Constant.DISTANCE_FEE));;
    if(distance.longValue()>Constant.DISTANCE_TEN_KILO_MITRE){
        distanceTotal =distanceTotal.add(new BigDecimal(distance.longValue()-Constant.DISTANCE_TEN_KILO_MITRE).multiply(new BigDecimal(Constant.DISTANCE_FEE_EXTRA))).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    result.setDistancePrice(distanceTotal.setScale(2, BigDecimal.ROUND_HALF_UP));
    total = total.add(distanceTotal.setScale(2, BigDecimal.ROUND_HALF_UP));
    //楼层费用
    if(order.getStorey()>0){
        BigDecimal storeyTotal = new BigDecimal(order.getStorey()).multiply(new BigDecimal(Constant.STOREY_FEE)).setScale(2, BigDecimal.ROUND_HALF_UP);
        total = total.add(storeyTotal);
        result.setStoryPrice(storeyTotal);
    }


    //类型基础费用
    Long moveTypeId = order.getMoveTypeId();
    MoveTypeEntity moveType = moveTypeService.getById(moveTypeId);
    total = total.add(moveType.getPrice());
    result.setTypePrice(moveType.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
    //服务费用
    BigDecimal serviceTotal  = BigDecimal.ZERO;
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
        serviceTotal =  serviceTotal.add(fee);
    }

    total = total.add(serviceTotal);
    result.setServicePrice(serviceTotal.setScale(2, BigDecimal.ROUND_HALF_UP));

    //货车费用
    if(order.getTruckId()!=null){
        TruckEntity truck = truckService.getById(order.getTruckId());
        if(truck!=null){

        }
        BigDecimal basePrice = truck.getBasePrice().setScale(2, BigDecimal.ROUND_HALF_UP);
        total =  total.add(basePrice);
        result.setTruckPrice(basePrice);
    }


    //额外费用
    if(order.getExtraFee()!=null){
        BigDecimal extraTotal = order.getExtraFee().setScale(2, BigDecimal.ROUND_HALF_UP);
        result.setExtraPrice(extraTotal);
        total =  total.add(extraTotal);
    }

    result.setTotalPrice(total.setScale(2, BigDecimal.ROUND_HALF_UP));
    return  result;
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

        //货车费用
        if(order.getTruckId()!=null){
            TruckEntity truck = truckService.getById(order.getUserId());
            BigDecimal basePrice = truck.getBasePrice();
            total =  total.add(basePrice);

        }
        return  total;
    }

    @Override
    public OrderInfoDto getInfoById(Long id) {
        OrderEntity orderEntity = baseMapper.selectById(id);
        OrderInfoDto orderInfoDto = covertOrderInfoDto(orderEntity);
        return orderInfoDto;

    }

    @Override
    public void updateStatus(Long id, Integer status) {
        OrderEntity orderEntity = baseMapper.selectById(id);
        if(orderEntity==null){
            throw  new  RRException("订单查询失败");

        }
        PriceItem priceItem = this.computePrice(orderEntity);
        orderEntity.setTotalPrice(priceItem.getTotalPrice());
        orderEntity.setStatus(status);
        baseMapper.updateById(orderEntity);

    }

    @Override
    public void confirm(Long id) {
        OrderEntity orderEntity = baseMapper.selectById(id);
        if(orderEntity==null){
            throw  new  RRException("订单查询失败");

        }
        PriceItem priceItem = this.computePrice(orderEntity);
        orderEntity.setTotalPrice(priceItem.getTotalPrice());
        orderEntity.setStatus(Constant.OrderStatus.Computed.getKey());
        baseMapper.updateById(orderEntity);

    }

    @Override
    public void editPrice(EditPriceDto editPriceDto) {
        if(editPriceDto.getId()==null||editPriceDto.getMessage()==null||(editPriceDto.getPrice()==0)){
            throw new RRException("请检查输入参数");
        }
        OrderEntity orderEntity = baseMapper.selectById(editPriceDto.getId());
        if(orderEntity==null){
            throw new RRException("查询失败");
        }
        orderEntity.setExtraFee(BigDecimal.valueOf(editPriceDto.getPrice()));
        orderEntity.setMessage(editPriceDto.getMessage());
        baseMapper.updateById(orderEntity);
    }

    @Override
    public void assignEmployee(AssignEmployeeDto assignEmployeeDto) {
        if(CollectionUtil.isEmpty(assignEmployeeDto.getEmpIds())||assignEmployeeDto.getId()==null){
            throw  new  RRException("参数错误");
        }
        OrderEntity orderEntity = baseMapper.selectById(assignEmployeeDto.getId());
        if(orderEntity==null){
            throw  new  RRException("订单不存在");
        }
        assignEmployeeDto.getEmpIds().forEach(empId->{


            EmployeeOrderEntity employeeOrderEntity = new EmployeeOrderEntity();
            EmployeeOrderEntity one = employeeOrderService.getOne(new LambdaQueryWrapper<EmployeeOrderEntity>()
                    .eq(EmployeeOrderEntity::getEmployeeId, empId)
                    .eq(EmployeeOrderEntity::getOrderId, assignEmployeeDto.getId()));
            if(one==null){
                employeeOrderEntity.setOrderId( assignEmployeeDto.getId());
                employeeOrderEntity.setEmployeeId(empId);
                employeeOrderService.save(employeeOrderEntity);
            }

        });
        List<EmployeeEntity> list = employeeService.listByIds(assignEmployeeDto.getEmpIds());
        list.forEach(employeeEntity -> {
            employeeEntity.setStatus(0);

        });
        employeeService.updateBatchById(list);
    }

    @Override
    public void payed(Long id) {
        OrderEntity orderEntity = baseMapper.selectById(id);
        if(orderEntity==null){
            throw  new  RRException("订单查询失败");

        }
        PriceItem priceItem = this.computePrice(orderEntity);
        orderEntity.setTotalPrice(priceItem.getTotalPrice());
        orderEntity.setStatus(Constant.OrderStatus.PAYED.getKey());
        baseMapper.updateById(orderEntity);

    }

    @Override
    public void complete(Long id) {
        OrderEntity orderEntity = baseMapper.selectById(id);
        if(orderEntity==null){
            throw  new  RRException("订单查询失败");

        }
        PriceItem priceItem = this.computePrice(orderEntity);
        orderEntity.setTotalPrice(priceItem.getTotalPrice());
        orderEntity.setStatus(Constant.OrderStatus.COMPLETED.getKey());
        baseMapper.updateById(orderEntity);

    }

    @Override
    public void startMove(Long id) {
        OrderEntity orderEntity = baseMapper.selectById(id);
        if(orderEntity==null){
            throw  new  RRException("订单查询失败");

        }
        PriceItem priceItem = this.computePrice(orderEntity);
        orderEntity.setTotalPrice(priceItem.getTotalPrice());
        orderEntity.setStatus(Constant.OrderStatus.MOVING.getKey());
        baseMapper.updateById(orderEntity);
    }
}