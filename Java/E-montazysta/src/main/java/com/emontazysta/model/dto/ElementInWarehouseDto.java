package com.emontazysta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ElementInWarehouseDto {
    private Long id;

    @PositiveOrZero(message = "Quantity cannot be negative")
    private int inWarehouseCount;
    @PositiveOrZero(message = "Quantity cannot be negative")
    private int inUnitCount;
    @NotBlank(message = "Rack cannot be empty")
    private String rack;
    @NotBlank(message = "Shelf cannot be empty")
    private String shelf;
    @NotNull(message = "Element id cannot be empty")
    private Long elementId;
    @NotNull(message = "Warehouse id cannot be empty")
    private Long warehouseId;
}