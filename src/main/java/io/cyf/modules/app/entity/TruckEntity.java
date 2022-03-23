package io.cyf.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 货车表
 * 
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:15
 */
@Data
@TableName("t_truck")
public class TruckEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 类型;1小型|2中型|3大型
	 */
	private Integer type;
	/**
	 * 状态;1空闲|2使用中
	 */
	private Integer status;
	/**
	 * 车牌号
	 */
	private String carNumber;
	/**
	 * 司机
	 */
	private Long driverId;
	/**
	 * 起步价
	 */
	private BigDecimal basePrice;
	/**
	 * 图片
	 */
	private String picture;
	/**
	 * 车厢尺寸
	 */
	private String size;
	/**
	 * 备注
	 */
	private String remark;

}
