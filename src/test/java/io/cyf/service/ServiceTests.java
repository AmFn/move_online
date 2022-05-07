package io.cyf.service;

import io.cyf.common.utils.DateUtils;
import io.cyf.modules.app.Dto.CreateOrderDto;
import io.cyf.modules.app.Dto.PriceItem;
import io.cyf.modules.app.Dto.SaveAddressDTO;
import io.cyf.modules.app.entity.OrderEntity;
import io.cyf.modules.app.service.OrderService;
import io.cyf.modules.app.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chenyufeng
 * @date 2022/4/21 13:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ServiceTests {

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
