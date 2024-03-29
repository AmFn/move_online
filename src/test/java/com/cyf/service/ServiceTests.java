package com.cyf.service;

import com.cyf.common.utils.SendMsgUtils;
import com.cyf.modules.app.Dto.SaveAddressDTO;
import com.cyf.modules.app.entity.OrderEntity;
import com.cyf.modules.app.service.OrderService;
import com.cyf.modules.app.Dto.PriceItem;
import com.cyf.modules.app.service.impl.SmsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyufeng
 * @date 2022/4/21 13:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ServiceTests {
    @Test
    public void sendTest() throws Exception {
        SmsServiceImpl smsService = new SmsServiceImpl();
        boolean b = SendMsgUtils.sendMsg("16685274469", "120");
        log.info("发送结果{}",b);
    }

    @Autowired
    OrderService orderService;
    @Test
   public void createOrderTest(){
        List<Long> serviceIds = new ArrayList<>();
        serviceIds.add(1L);
        serviceIds.add(2L);
        serviceIds.add(3L);
        SaveAddressDTO nowAddr = new SaveAddressDTO("116.315761,39.990097","北京市海淀区北京大学燕园校区","图书馆");
        SaveAddressDTO newAddr = new SaveAddressDTO("116.370407,39.9619","北京市海淀区新街口外大街19号","图书馆");
//        CreateOrderDto createOrderDto = new CreateOrderDto(
//                1L,2L,serviceIds,nowAddr,newAddr, DateUtils.addDateDays(new Date(),10),2
//        );

//        orderService.create(createOrderDto);
    }

    @Test
   public void computeTest(){
        OrderEntity order = orderService.getById(2L);
        PriceItem priceItem = orderService.computePrice(order);

        log.info(priceItem.toString());
    }
}
