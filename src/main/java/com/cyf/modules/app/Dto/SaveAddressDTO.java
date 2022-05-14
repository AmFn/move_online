package com.cyf.modules.app.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author chenyufeng
 * @date 2022/4/1 13:46
 */
@Data
@AllArgsConstructor
public class SaveAddressDTO {
    @NotNull(message = "location不能为空")
    private  String location;
    @NotNull(message = "地址不能为空")
    private String address;
    @NotNull(message = "详细地址不能为空")
    private String detail;
    public SaveAddressDTO(){

    }
}
