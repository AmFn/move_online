package io.cyf.modules.app.Dto;

import lombok.Data;

import java.util.List;

/**
 * @author chenyufeng
 * @date 2022/4/1 13:00
 */
@Data
public class CitySearch  extends  BaseResponse{
    private  List<Poi> pois;

}
