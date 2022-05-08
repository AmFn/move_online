package io.cyf.modules.app.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.cyf.common.exception.RRException;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.R;
import io.cyf.modules.app.Dto.AddCommentDto;
import io.cyf.modules.app.Dto.CommitDto;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.cyf.modules.app.entity.CommentEntity;
import io.cyf.modules.app.service.CommentService;



/**
 * 
 *
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-05-08 18:48:30
 */
@RestController
@RequestMapping("app/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 列表
     */
    @RequestMapping("/list")

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
        commentService.save(commentEntity);
        return R.ok();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")

    public R info(@PathVariable("id") Long id){
		CommentEntity comment = commentService.getById(id);

        return R.ok().put("comment", comment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")

    public R save(@RequestBody CommentEntity comment){
		commentService.save(comment);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")

    public R update(@RequestBody CommentEntity comment){
		commentService.updateById(comment);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")

    public R delete(@RequestBody Long[] ids){
		commentService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
