package io.cyf.modules.app.Dto;

import lombok.Data;

import java.util.List;

/**
 * @author chenyufeng
 * @date 2022/4/1 14:09
 */
@Data
public class CreateOrderDto {
    private Long uid;
    private Long moveTypeId;
    private  List<Long> extraServiceIds;
    private SaveAddressDTO nowAddress;
    private SaveAddressDTO newAddress;
}
