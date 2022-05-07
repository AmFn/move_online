package io.cyf.modules.app.Dto;

import lombok.Data;
import sun.util.resources.ga.LocaleNames_ga;

import java.util.List;

/**
 * @author chenyufeng
 * @date 2022/5/7 13:20
 */
@Data
public class AssignEmployeeDto {
   private   List<Long> empIds;
    private Long id;
}
