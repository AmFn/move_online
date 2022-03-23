package io.cyf.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 搬家类型
 * 
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
@Data
@TableName("t_move_type")
public class MoveTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 类型名称
	 */
	private String type;
	/**
	 * 基础价格
	 */
	private BigDecimal price;
	/**
	 * 类型描述
	 */
	private String description;
	/**
	 * 需要人数
	 */
	private Integer requirePeople;

}
