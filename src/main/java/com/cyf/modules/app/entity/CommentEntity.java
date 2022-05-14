package com.cyf.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-05-08 18:48:30
 */
@Data
@TableName("t_comment")
public class CommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long uid;
	/**
	 * 
	 */
	private Long orderId;
	/**
	 * 
	 */
	private Long time;
	/**
	 * 
	 */
	private String content;
	/**
	 * 
	 */
	private Integer isDel;

}
