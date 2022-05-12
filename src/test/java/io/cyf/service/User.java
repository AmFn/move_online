package io.cyf.service;

import lombok.Builder;
import lombok.Data;

/**
 * @author chenyufeng
 * @date 2022/5/11 23:01
 */
@Data
@Builder
public class User {
    private String name;
    private  String age;
}
