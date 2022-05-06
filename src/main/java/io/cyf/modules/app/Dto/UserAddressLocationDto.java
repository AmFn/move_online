package io.cyf.modules.app.Dto;

import lombok.Data;

/**
 * @author chenyufeng
 * @date 2022/5/5 11:18
 */
@Data
public class UserAddressLocationDto {
    private  Long id;
    private  String nowLocation;
    private  String newLocation;
}
