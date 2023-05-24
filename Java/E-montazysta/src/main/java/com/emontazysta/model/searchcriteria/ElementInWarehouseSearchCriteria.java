package com.emontazysta.model.searchcriteria;

import lombok.Data;

import java.util.List;

@Data
public class ElementInWarehouseSearchCriteria {

    private Long minCount;
    private List<String> warehouseId;
    private String elementId;
}