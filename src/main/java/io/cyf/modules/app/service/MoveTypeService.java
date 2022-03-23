package io.cyf.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cyf.common.utils.PageUtils;
import io.cyf.modules.app.entity.MoveTypeEntity;

import java.util.Map;

/**
 * 搬家类型
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
public interface MoveTypeService extends IService<MoveTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

