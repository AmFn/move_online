package com.cyf.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyf.common.utils.PageUtils;
import com.cyf.modules.app.entity.TruckEntity;

import java.util.Map;

/**
 * 货车表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:15
 */
public interface TruckService extends IService<TruckEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void distribution(Long id, Long orderId);



    TruckEntity getOneByType(Integer type);
}

