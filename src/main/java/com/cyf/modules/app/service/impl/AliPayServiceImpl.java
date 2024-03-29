package com.cyf.modules.app.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyf.common.exception.RRException;
import com.cyf.common.utils.Constant;
import com.cyf.modules.app.entity.CompensateEntity;
import com.cyf.modules.app.entity.OrderEntity;
import com.cyf.modules.app.service.OrderService;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.cyf.common.utils.AliPayTradeState;
import com.cyf.modules.app.service.AliPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
@Slf4j
public class AliPayServiceImpl implements AliPayService {
    @Resource
    private OrderService orderService;
    @Resource
    private AlipayClient alipayClient;
    @Resource
    private Environment config;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String tradeCreate(Long orderId) {
        try {
            OrderEntity orderInfo = orderService.getById(orderId);
            orderInfo.setOrderNo(IdWorker.getIdStr());
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            request.setNotifyUrl(config.getProperty("alipay.notify-url"));
            request.setReturnUrl(config.getProperty("alipay.return-url"));
            //组装当前业务方法的请求参数
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderInfo.getOrderNo());
            bizContent.put("total_amount", orderInfo.getTotalPrice().setScale(2));
            bizContent.put("subject", "move_online");
            bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
            request.setBizContent(bizContent.toString());

            orderService.updateById(orderInfo);
            log.info("bizcontent===》{}", bizContent.toString());
            //执行请求，调用支付宝接口
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
            if (response.isSuccess()) {
                return response.getBody();
            } else {
                throw new RuntimeException("创建支付交易失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("创建支付交易失败");
        }
    }

    @Override
    public String queryOrder(String orderNo) {
        try {
            log.info("查单接口调用 ===> {}", orderNo);
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderNo);
            request.setBizContent(bizContent.toString());
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                log.info("调用成功，返回结果 ===> " + response.getBody());
                return response.getBody();
            } else {
                log.info("调用失败，返回码 ===> " + response.getCode() + ", 返回描述 ===> " + response.getMsg());
                return null;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("查单接口的调用失败");
        }
    }

    @Override
    public void checkOrderStatus(String orderNo) {
        log.warn("根据订单号核实订单状态 ===> {}", orderNo);
        String result = this.queryOrder(orderNo);
        //解析查单响应结果
        Gson gson = new Gson();

        HashMap<String, LinkedTreeMap> resultMap = gson.fromJson(result, HashMap.class);
        LinkedTreeMap alipayTradeQueryResponse = resultMap.get("alipay_trade_query_response");
        String tradeStatus = (String) alipayTradeQueryResponse.get("trade_status");
        if (AliPayTradeState.NOTPAY.getType().equals(tradeStatus)) {
            log.warn("核实订单未支付 ===> {}", orderNo);
        }
        if (AliPayTradeState.SUCCESS.getType().equals(tradeStatus)) {
            log.warn("核实订单已支付 ===> {}", orderNo);
            //如果订单已支付，则更新订单状态
            orderService.updateStatus(Long.valueOf(orderNo), Constant.OrderStatus.PAYED.getKey());
        }
    }

    @Override
    public void refund(String orderNo, String reason) {

    }

    @Override
    public String refundByCompensateItem(CompensateEntity item) {
        String orderNo= orderService.getOrderNoById(item.getOrderId());
        if(StringUtils.isBlank(orderNo)){
            throw  new RRException("订单号错误");
        }


        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("trade_no",orderNo);
        bizContent.put("refund_amount", item.getCompensate().setScale(2));
        bizContent.put("out_request_no", "HZ01RF001");


        request.setBizContent(bizContent.toString());
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            log.info("调用成功");
        } else {
            log.info("调用失败");
        }
        return null;
    }
}
