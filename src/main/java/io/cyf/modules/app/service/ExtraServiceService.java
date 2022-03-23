package io.cyf.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cyf.common.utils.PageUtils;
import io.cyf.modules.app.entity.ExtraServiceEntity;

import java.util.Map;

/**
 * 额外服务表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
public interface ExtraServiceService extends IService<ExtraServiceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

