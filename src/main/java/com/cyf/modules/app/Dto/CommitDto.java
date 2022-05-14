package com.cyf.modules.app.Dto;

import lombok.Data;

/**
 * @author chenyufeng
 * @date 2022/5/8 18:55
 */

@Data
public class CommitDto {
    private String username;
    private String avatar;
    private String date;
    private String content;

}
