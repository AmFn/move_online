package io.cyf.modules.app.Dto;

import lombok.Data;

/**
 * @author chenyufeng
 * @date 2022/5/4 22:58
 */
@Data
public class UserAddressDto {
    private  Long userId;
    private  String phone;
    private  String realName;
    private  String nowAddress;
    private String nowLocation;
    private  String newAddress;
    private  String newLocation;

}
