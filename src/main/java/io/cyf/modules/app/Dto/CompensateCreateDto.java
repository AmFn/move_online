package io.cyf.modules.app.Dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chenyufeng
 * @date 2022/5/7 16:41
 */
@Data
public class CompensateCreateDto {
    private Long orderId;
    private String itemName;
    private  Integer itemPrice;
    private String remark;
    private Integer compensate;

    private String picture;


}
