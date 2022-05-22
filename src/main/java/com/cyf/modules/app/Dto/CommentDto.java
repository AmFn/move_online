package com.cyf.modules.app.Dto;

import lombok.Data;

/**
 * @author chenyufeng
 * @date 2022/5/14 15:18
 */
@Data
public class CommentDto {
    private String phone;
    private  String realName;
    /**
     *
     */
    private Long orderId;
    /**
     *
     */
    private String time;
    /**
     *
     */
    private String content;

    private String type;
}
