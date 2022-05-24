package com.cyf.modules.app.service;

import com.cyf.modules.app.entity.CompensateEntity;

import java.util.Map;

public interface AliPayService {
    String tradeCreate(Long orderId);

    String queryOrder(String orderNo);

    void checkOrderStatus(String orderNo);
//    void processOrder(Map<String, String> params);
//
//    void cancelOrder(String orderNo);
//
//    String queryOrder(String orderNo);
//
//    void checkOrderStatus(String orderNo);
//
    void refund(String orderNo, String reason);

    String refundByCompensateItem(CompensateEntity item);
//
//    String queryRefund(String orderNo);
//
//    String queryBill(String billDate, String type);

}
