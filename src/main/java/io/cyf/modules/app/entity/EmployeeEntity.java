package io.cyf.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 员工表
 * 
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
@Data
@TableName("t_employee")
public class EmployeeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 状态;0繁忙|1空闲
	 */
	private Integer status;
	/**
	 * 名字
	 */
	private String name;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 类型;0搬家人员|1司机|2服务人员
	 */
	private Integer type;
	/**
	 * 是否删除;0未删除|1删除
	 */
	@TableLogic
	private Integer isDel;

}
