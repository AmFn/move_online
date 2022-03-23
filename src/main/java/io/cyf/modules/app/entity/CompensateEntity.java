package io.cyf.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 赔偿物品表
 * 
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
@Data
@TableName("t_compensate")
public class CompensateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 订单id
	 */
	private Long orderId;
	/**
	 * 物品名称
	 */
	private String itemName;
	/**
	 * 物品价格
	 */
	private String itemPrice;
	/**
	 * 审核是否通过;0未通过|1通过
	 */
	private Integer isAudit;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 赔偿价格
	 */
	private BigDecimal compensate;
	/**
	 * 损坏照片
	 */
	private String picture;
	/**
	 * 是否删除;0未删除|1删除
	 */
	private Integer isDel;

}
