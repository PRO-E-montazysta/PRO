package com.emontazysta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ElementInWarehouseDto {
    private Long id;

    private int inWarehouseCount;
    private int inUnitCount;
    private String rack;
    private String shelf;
    private Long elementId;
    private Long warehouseId;
}