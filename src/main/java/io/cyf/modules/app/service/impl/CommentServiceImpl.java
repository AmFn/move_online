package io.cyf.modules.app.service.impl;

import cn.hutool.core.date.DateUtil;
import io.cyf.common.utils.DateUtils;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.Query;
import io.cyf.modules.app.Dto.CommitDto;
import io.cyf.modules.app.entity.UserEntity;
import io.cyf.modules.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import io.cyf.modules.app.dao.CommentDao;
import io.cyf.modules.app.entity.CommentEntity;
import io.cyf.modules.app.service.CommentService;


@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {
    @Autowired
    private UserService userService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CommentEntity> page = this.page(
                new Query<CommentEntity>().getPage(params),
                new QueryWrapper<CommentEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CommitDto> getAllCommit() {
        List<CommentEntity> commentEntities = getBaseMapper().selectList(null);
        List<CommitDto> res = new ArrayList<>();
        commentEntities.forEach(comment->{
            CommitDto commitDto = new CommitDto();
            UserEntity user = userService.getById(comment.getUid());
            commitDto.setAvatar(user.getAvatar());
            commitDto.setContent(comment.getContent());
            if(comment.getTime()!=null){
                commitDto.setDate(DateUtil.format(new Date(comment.getTime()), DateUtils.DATE_PATTERN));
            }

            res.add(commitDto);
        });

        return res.stream().limit(500).collect(Collectors.toList());

    }
}