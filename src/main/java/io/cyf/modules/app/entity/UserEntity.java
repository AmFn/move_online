package io.cyf.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * 
 * @author chenyufeng
 * @email chenyufengkkk@163.com
 * @date 2022-03-22 09:43:16
 */
@Data
@TableName("t_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 电话号码
	 */
	private String phone;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 地址id
	 */
	private Long addressId;
	/**
	 * 新地址id
	 */
	private Long newAddressId;
	/**
	 * 类型;1普通用户|2管理员
	 */
	private Integer type;

}
