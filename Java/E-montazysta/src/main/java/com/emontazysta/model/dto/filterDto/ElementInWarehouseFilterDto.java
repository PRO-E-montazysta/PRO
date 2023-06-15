package com.emontazysta.model.dto.filterDto;

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
public class ElementInWarehouseFilterDto {
    private Long id;
    private int inWarehouseCount;
    private String rack;
    private String shelf;
    private String element;
    private String warehouse;
    private boolean deleted;
}