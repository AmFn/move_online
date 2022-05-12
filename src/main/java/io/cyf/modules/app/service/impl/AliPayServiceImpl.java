package io.cyf.modules.app.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.RefundInfo;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import io.cyf.common.utils.AliPayTradeState;
import io.cyf.common.utils.Constant;
import io.cyf.modules.app.entity.OrderEntity;
import io.cyf.modules.app.service.AliPayService;
import io.cyf.modules.app.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

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
            //生成订单
            log.info("生成订单");
            OrderEntity orderInfo = orderService.getById(orderId);

            //调用支付宝接口
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            //配置需要的公共请求参数

            request.setNotifyUrl(config.getProperty("alipay.notify-url"));

            request.setReturnUrl(config.getProperty("alipay.return-url"));

            //组装当前业务方法的请求参数
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderInfo.getId());

            bizContent.put("total_amount", orderInfo.getTotalPrice().setScale(2));
            bizContent.put("subject", "move_online");
            bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

            request.setBizContent(bizContent.toString());
            log.info("bizcontent===》{}",bizContent.toString());
            //执行请求，调用支付宝接口
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request);

            if(response.isSuccess()){
                log.info("调用成功，返回结果 ===> " + response.getBody());
                return response.getBody();
            } else {
                log.info("调用失败，返回码 ===> " + response.getCode() + ", 返回描述 ===> " + response.getMsg());
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
            if(response.isSuccess()){
                log.info("调用成功，返回结果 ===> " + response.getBody());
                return response.getBody();
            } else {
                log.info("调用失败，返回码 ===> " + response.getCode() + ", 返回描述 ===> " + response.getMsg());
                //throw new RuntimeException("查单接口的调用失败");
                return null;//订单不存在
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("查单接口的调用失败");
        }
    }
    /**
     * 根据订单号调用支付宝查单接口，核实订单状态
     * 如果订单未创建，则更新商户端订单状态
     * 如果订单未支付，则调用关单接口关闭订单，并更新商户端订单状态
     * 如果订单已支付，则更新商户端订单状态，并记录支付日志
     * @param orderNo
     */
    @Override
    public void checkOrderStatus(String orderNo) {

        log.warn("根据订单号核实订单状态 ===> {}", orderNo);

        String result = this.queryOrder(orderNo);

        //订单未创建
//        if(result == null){
//            log.warn("核实订单未创建 ===> {}", orderNo);
//            //更新本地订单状态
//            orderInfoService.updateStatusByOrderNo(orderNo, OrderStatus.CLOSED);
//        }

        //解析查单响应结果
        Gson gson = new Gson();
        HashMap<String, LinkedTreeMap> resultMap = gson.fromJson(result, HashMap.class);
        LinkedTreeMap alipayTradeQueryResponse = resultMap.get("alipay_trade_query_response");

        String tradeStatus = (String)alipayTradeQueryResponse.get("trade_status");
        if(AliPayTradeState.NOTPAY.getType().equals(tradeStatus)){
            log.warn("核实订单未支付 ===> {}", orderNo);

            //如果订单未支付，则调用关单接口关闭订单
//            this.closeOrder(orderNo);

            // 并更新商户端订单状态
//            orderInfoService.updateStatusByOrderNo(orderNo, OrderStatus.CLOSED);
        }

        if(AliPayTradeState.SUCCESS.getType().equals(tradeStatus)){
            log.warn("核实订单已支付 ===> {}", orderNo);

            //如果订单已支付，则更新商户端订单状态
            orderService.updateStatus(Long.valueOf(orderNo), Constant.OrderStatus.PAYED.getKey());


        }

    }


}
