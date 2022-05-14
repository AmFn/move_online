package com.cyf.modules.app.Dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author chenyufeng
 * @date 2022/5/14 11:59
 */
@Data
@Builder
public class EmpTemplate {
    @ExcelProperty("员工姓名")
    private  String name;
    @ExcelProperty("员工手机号")
    private  String phone;
    @ExcelProperty("员工类型")
    private  String type;
}
