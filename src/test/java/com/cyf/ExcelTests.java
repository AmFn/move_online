package com.cyf;


import com.alibaba.excel.EasyExcel;
import com.cyf.common.utils.EmpExcelListener;
import com.cyf.modules.app.entity.EmployeeEntity;

/**
 * @author chenyufeng
 * @date 2022/5/14 10:39
 */
public class ExcelTests {

        public static void main(String[] args) {
            String fileName = "D:\\study\\GraduationDesign\\code\\code_springboot\\emp.xlsx";
            EasyExcel.read(fileName, EmployeeEntity.class,new EmpExcelListener()).sheet().doRead();


    }
}
