package com.cyf.modules.app.Dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chenyufeng
 * @date 2022/5/6 22:42
 */
@Data
public class PriceItem {
    //距离费用
    private BigDecimal distancePrice ;
    //楼层费用
    private BigDecimal storyPrice;
    //类型基础费用
    private BigDecimal typePrice;
    //服务费用
    private BigDecimal servicePrice;
    //车辆费用
    private BigDecimal truckPrice;
    //额外费用
    private BigDecimal extraPrice;
    //总价
    private  BigDecimal totalPrice;


    public PriceItem(){
        BigDecimal zero  = BigDecimal.ZERO;
        this.distancePrice = zero ;
        this.storyPrice = zero;
        this.typePrice = zero;
        this.servicePrice = zero;
        this.truckPrice = zero;
        this.totalPrice = zero;
        this.extraPrice = zero;
    }



}
