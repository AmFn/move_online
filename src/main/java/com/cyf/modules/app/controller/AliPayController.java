package com.cyf.modules.app.controller;

import com.cyf.common.utils.R;
import com.cyf.modules.app.service.AliPayService;
import com.cyf.modules.app.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/ali-pay")
@Api(tags = "网站支付宝支付")
@Slf4j
public class AliPayController {

    @Resource
    private AliPayService aliPayService;

    @Resource
    private Environment config;

    @Resource
    private OrderService orderInfoService;

    @ApiOperation("统一收单下单并支付页面接口的调用")
    @GetMapping("/page/pay/{orderId}")
    public R tradePagePay(@PathVariable("orderId") Long orderId){

        log.info("统一收单下单并支付页面接口的调用");

        String formStr = aliPayService.tradeCreate(orderId);
        return R.ok().put("formStr", formStr);
    }


    @ApiOperation("查询订单：测试订单状态用")
    @GetMapping("/query/{orderNo}")
    public R queryOrder(@PathVariable("orderNo") String orderNo)  {

        log.info("查询订单");

        String result = aliPayService.queryOrder(orderNo);
        return R.ok().put("result", result);

    }



}
