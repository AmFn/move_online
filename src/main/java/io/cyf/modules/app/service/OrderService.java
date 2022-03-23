package io.cyf.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cyf.common.utils.PageUtils;
import io.cyf.modules.app.entity.OrderEntity;

import java.util.Map;

/**
 * 订单表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:15
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

