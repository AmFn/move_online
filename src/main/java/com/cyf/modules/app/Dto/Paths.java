
package com.cyf.modules.app.Dto;
import lombok.Data;

import java.util.List;

@Data
public class Paths {

    private String distance;
    private String restriction;
    private List<Steps> steps;

}