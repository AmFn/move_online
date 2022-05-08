package io.cyf.modules.app.Dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author chenyufeng
 * @date 2022/5/8 19:33
 */
@Data
public class AddCommentDto {

    private  Long uid;

    private Long orderId;
    private  String content;
}
