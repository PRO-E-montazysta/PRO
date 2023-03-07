package com.emontazysta.model.searchcriteria;

import lombok.Data;

@Data
public class ToolSearchCriteria {

    private String name;
    private String code;
    private Long warehouse_Id;
    private Long toolType_Id;
}
