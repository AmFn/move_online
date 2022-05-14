package com.cyf.modules.app.Dto;

import lombok.Data;

/**
 * @author chenyufeng
 * @date 2022/4/1 10:07
 */
@Data
public class BaseResponse {
    private String status;
    private String info;
    private String infocode;
}
