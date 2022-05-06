package io.cyf.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cyf.common.utils.PageUtils;
import io.cyf.modules.app.entity.ServiceOrderEntity;

import java.util.Map;

/**
 * 服务_订单表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-04-21 17:21:02
 */
public interface ServiceOrderService extends IService<ServiceOrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

