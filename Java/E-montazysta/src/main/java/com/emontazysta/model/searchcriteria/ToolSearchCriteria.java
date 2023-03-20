package com.emontazysta.model.searchcriteria;

import lombok.Data;

import java.util.List;

@Data
public class ToolSearchCriteria {

    private String name;
    private String code;
    private List<String> warehouse_Id;
    private List<String> toolType_Id;
}
