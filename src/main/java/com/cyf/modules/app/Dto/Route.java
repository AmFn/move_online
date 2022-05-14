
package com.cyf.modules.app.Dto;
import lombok.Data;

import java.util.List;

@Data
public class Route {

    private String origin;
    private String destination;
    private String taxi_cost;
    private List<Paths> paths;


}