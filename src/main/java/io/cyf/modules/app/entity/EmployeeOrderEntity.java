package io.cyf.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 员工_订单表
 * 
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-04-21 17:21:02
 */
@Data
@TableName("t_employee_order")
public class EmployeeOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 订单id
	 */
	private Long orderId;
	/**
	 * 员工id
	 */
	private Long employeeId;
	/**
	 * 是否删除;0未删除|1删除
	 */
	@TableLogic
	private Integer isDel;

}
