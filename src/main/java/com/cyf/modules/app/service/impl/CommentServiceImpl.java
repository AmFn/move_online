package com.cyf.modules.app.service.impl;

import cn.hutool.core.date.DateUtil;
import com.cyf.common.utils.PageCovertUtil;
import com.cyf.modules.app.Dto.CommentDto;
import com.cyf.modules.app.entity.UserEntity;
import com.cyf.common.utils.DateUtils;
import com.cyf.common.utils.PageUtils;
import com.cyf.common.utils.Query;
import com.cyf.modules.app.Dto.CommitDto;
import com.cyf.modules.app.service.UserService;
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
import com.cyf.modules.app.dao.CommentDao;
import com.cyf.modules.app.entity.CommentEntity;
import com.cyf.modules.app.service.CommentService;

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
        List<CommentDto> res = new ArrayList<>();
        page.getRecords().forEach(commentEntity -> {
            CommentDto commentDto = new CommentDto();
            commentDto.setOrderId(commentEntity.getOrderId());
            commentDto.setContent(commentEntity.getContent());
            UserEntity user = userService.getById(commentEntity.getUid());
            if (user != null) {
                commentDto.setRealName(user.getRealName());
                commentDto.setPhone(user.getPhone());
            }
            commentDto.setTime(DateUtil.format(new Date(commentEntity.getTime()), DateUtils.DATE_PATTERN));
            if (commentEntity.getGrade() > 0) {
                commentDto.setType(commentEntity.getGrade() >= 3 ? "好评" : "差评");
            }
            res.add(commentDto);
        });
        IPage<CommentDto> newPage = PageCovertUtil.pageVoCovert(page, CommentDto.class);
        newPage.setRecords(res);
        return new PageUtils(newPage);
    }

    @Override
    public List<CommitDto> getAllCommit() {
        List<CommentEntity> commentEntities = getBaseMapper().selectList(null);
        List<CommitDto> res = new ArrayList<>();
        commentEntities.forEach(comment -> {
            CommitDto commitDto = new CommitDto();
            UserEntity user = userService.getById(comment.getUid());
            commitDto.setAvatar(user.getAvatar());
            commitDto.setContent(comment.getContent());
            if (comment.getTime() != null) {
                commitDto.setDate(DateUtil.format(new Date(comment.getTime()), DateUtils.DATE_PATTERN));
            }
            res.add(commitDto);
        });
        return res.stream().limit(500).collect(Collectors.toList());
    }
}