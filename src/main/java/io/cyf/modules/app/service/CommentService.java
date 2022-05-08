package io.cyf.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cyf.common.utils.PageUtils;
import io.cyf.modules.app.Dto.CommitDto;
import io.cyf.modules.app.entity.CommentEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-05-08 18:48:30
 */
public interface CommentService extends IService<CommentEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CommitDto> getAllCommit();
}

