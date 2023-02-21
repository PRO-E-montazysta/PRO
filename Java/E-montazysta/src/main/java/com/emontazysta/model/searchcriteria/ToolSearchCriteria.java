package com.emontazysta.model.searchcriteria;

import lombok.Data;
import java.util.Date;

@Data
public class ToolSearchCriteria {

    private Long id;
    private String name;
    private Date createdAt;
    private String code;
    private Long toolReleases_Id;
    private Long warehouse_Id;
    private Long ToolEvent_Id;
    private Long ToolType_Id;
}
