package com.cyf.modules.app.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author chenyufeng
 * @date 2022/5/12 21:18
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
