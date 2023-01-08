package com.emontazysta.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ToolTypeDto {
    private Long id;

    private String name;

    private int inServiceCount;

    private int criticalNumber;

    private int availableCount;
}
