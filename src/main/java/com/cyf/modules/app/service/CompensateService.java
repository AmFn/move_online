package com.cyf.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyf.common.utils.PageUtils;
import com.cyf.modules.app.Dto.CompensateCreateDto;
import com.cyf.modules.app.entity.CompensateEntity;

import java.util.Map;

/**
 * 赔偿物品表
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
public interface CompensateService extends IService<CompensateEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveCompensate(CompensateCreateDto compensate);
}

