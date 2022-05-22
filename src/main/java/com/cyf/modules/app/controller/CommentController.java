package com.cyf.modules.app.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.cyf.common.exception.RRException;
import com.cyf.common.utils.PageUtils;
import com.cyf.common.utils.R;
import com.cyf.modules.app.Dto.AddCommentDto;
import com.cyf.modules.app.Dto.CommitDto;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cyf.modules.app.entity.CommentEntity;
import com.cyf.modules.app.service.CommentService;



/**
 * 
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-05-08 18:48:30
 */
@RestController
@RequestMapping("app/comment")
@Api(tags="评论管理")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 列表
     */
    @GetMapping("/list")

    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = commentService.queryPage(params);

        return R.ok().put("page", page);
    }

    @GetMapping("/list_baberrage")
    public R baberrage(){
        List<CommitDto> list = commentService.getAllCommit();
        return R.ok().put("page", list);
    }

    @PostMapping("/add_comment")
    public R addComment(@RequestBody AddCommentDto dto){

        if(dto.getOrderId()==null ||dto.getUid()==null || StringUtils.isBlank(dto.getContent())){
            throw new RRException("参数错误");
        }
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(dto.getContent());
        commentEntity.setUid(dto.getUid());
        commentEntity.setOrderId(dto.getOrderId());
        commentEntity.setTime(System.currentTimeMillis());
        commentEntity.setIsDel(0);
        commentService.save(commentEntity);
        return R.ok();
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")

    public R info(@PathVariable("id") Long id){
		CommentEntity comment = commentService.getById(id);

        return R.ok().put("comment", comment);
    }

    /**
     * 保存
     */
    @PostMapping("/save")

    public R save(@RequestBody CommentEntity comment){
		commentService.save(comment);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")

    public R update(@RequestBody CommentEntity comment){
		commentService.updateById(comment);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")

    public R delete(@RequestBody Long[] ids){
		commentService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
