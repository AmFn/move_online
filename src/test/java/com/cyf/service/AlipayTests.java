package com.cyf.service;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.cyf.modules.app.service.AliPayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.HashMap;

@SpringBootTest
@Slf4j
public class AlipayTests {

    @Resource
    private Environment config;
    @Resource
    private AliPayService aliPayService;
    @Test
    public void testAlipayConfig(){
        String s = aliPayService.queryOrder(String.valueOf(2));  Gson gson = new Gson();
        HashMap<String, LinkedTreeMap> resultMap = gson.fromJson(s, HashMap.class);
        LinkedTreeMap alipayTradeQueryResponse = resultMap.get("alipay_trade_query_response");

        String tradeStatus = (String)alipayTradeQueryResponse.get("trade_status");
        System.out.println(tradeStatus);

    }
}
