package io.cyf.modules.app.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 订单表
 * 
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:15
 */
@Data
@TableName("t_order")
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 订单状态;0查询中|1计价完成|2进行中|3已完成|4已支付|5售后中
	 */
	private Integer status;
	/**
	 * 搬家类型
	 */
	private Long moveTypeId;
	/**
	 * 总价格
	 */
	private BigDecimal totalPrice;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 货车id
	 */
	private Long truckId;
	/**
	 * 源地址
	 */
	private String sourceLocation;
	/**
	 * 目的地址
	 */
	private String destinationLocation;
	/**
	 * 距离
	 */
	private BigDecimal distance;

	private Long sourceAddressId;

	private Long destinationAddressId;
	/**
	 * 额外费用
	 */
	private BigDecimal extraFee;
	/**
	 * 楼层;有电梯默认为0
	 */
	private Integer storey;
	/**
	 * 预计需要时间
	 */
	private Integer estimatedTime;
	/**
	 * 搬家时间
	 */
	private Date moveTime;
	/**
	 * 消息
	 */
	private String message;
	/**
	 * 是否删除;0未删除|1删除
	 */
	@TableLogic
	private Integer isDel;




}
