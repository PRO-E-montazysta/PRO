package com.emontazysta.model.searchcriteria;

import lombok.Data;
import java.util.Date;

@Data
public class ToolSearchCriteria {

    private String name;
    private String code;
    private Long warehouse_Id;
    private Long ToolType_Id;
}
