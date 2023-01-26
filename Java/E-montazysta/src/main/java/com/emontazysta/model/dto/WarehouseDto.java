package com.emontazysta.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WarehouseDto {

    private Long id;
    private String name;
    private String description;
    private String openingHours;
}
