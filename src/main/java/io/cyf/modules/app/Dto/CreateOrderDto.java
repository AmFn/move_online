package io.cyf.modules.app.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author chenyufeng
 * @date 2022/4/1 14:09
 */
@Data
@AllArgsConstructor
public class CreateOrderDto {
    @NotNull(message="用户不能为空")
    private Long uid;
    @NotNull(message="搬家类型不能为空")
    private Long moveTypeId;
    private  List<Long> extraServiceIds;

    @NotNull(message = "旧地址不能为空")
    private SaveAddressDTO nowAddress;
    @NotNull(message = "新地址不能为空")
    private SaveAddressDTO newAddress;
    @Future(message = "搬家日期不能小于当前时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date moveTime;


    @NotNull(message = "楼层不能为空")
    private Integer storey;
    public CreateOrderDto(){

    }
}
