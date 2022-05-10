package io.cyf.modules.app.service;

import java.util.Map;

/**
 * @author chenyufeng
 * @date 2022/5/10 17:06
 */
public interface SmsService {
    Map<String, Object> sendMsg(String phone);
}
