package io.cyf.modules.app.Dto;

import lombok.Data;

/**
 * @author chenyufeng
 * @date 2022/5/7 11:16
 */
@Data
public class EditPriceDto {
    private Long id;
    private String message;
    private double price;
}
