package io.cyf.modules.app.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.fasterxml.jackson.databind.ObjectReader;
import io.cyf.common.utils.R;
import io.cyf.modules.app.service.AliPayService;
import io.cyf.modules.app.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;


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
        //支付宝开放平台接受 request 请求对象后
        // 会为开发者生成一个html 形式的 form表单，包含自动提交的脚本
        String formStr = aliPayService.tradeCreate(orderId);
        //我们将form表单字符串返回给前端程序，之后前端将会调用自动提交脚本，进行表单的提交
        //此时，表单会自动提交到action属性所指向的支付宝开放平台中，从而为用户展示一个支付页面
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
