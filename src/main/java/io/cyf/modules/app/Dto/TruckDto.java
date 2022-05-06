package io.cyf.modules.app.Dto;

import com.baomidou.mybatisplus.annotation.IdType;
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

public class TruckDto implements Serializable {
	private static final long serialVersionUID = 1L;


	private Long id;

	private Integer type;

	private Integer status;

	private String carNumber;

	private Long driverId;

	private BigDecimal basePrice;

	private String picture;

	private String size;

	private String remark;
	private String driverName;

}
