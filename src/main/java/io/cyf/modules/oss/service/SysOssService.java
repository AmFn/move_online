/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.cyf.modules.oss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cyf.common.utils.PageUtils;
import io.cyf.modules.oss.entity.SysOssEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件上传
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysOssService extends IService<SysOssEntity> {

	PageUtils queryPage(Map<String, Object> params);

    String uploadFileAvatar(MultipartFile file);
}
