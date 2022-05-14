package com.cyf.modules.app.Dto;

import com.cyf.modules.app.entity.*;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author chenyufeng
 * @date 2022/5/5 14:23
 */
@Data
public class OrderInfoDto {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private Integer status;
    private Long moveTypeId;
    private BigDecimal totalPrice;
    private Date createTime;
    private Long truckId;
    private String sourceLocation;
    private String destinationLocation;
    private BigDecimal distance;
    private Long sourceAddressId;
    private Long destinationAddressId;
    private BigDecimal extraFee;
    private Integer storey;
    private Integer estimatedTime;
    private Date moveTime;
    private String message;
    private Integer isDel;
    private MoveTypeEntity moveTypeEntity;
    private List<ExtraServiceEntity> extraServices;
    private List<EmployeeEntity> employeeEntities;
    private TruckEntity truckEntity;
    private AddressEntity sourceAddressEntity;
    private  AddressEntity destinationAddressEntity;
    private  UserEntity userEntity;
}
