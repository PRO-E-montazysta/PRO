package com.emontazysta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDto {

    private Long id;
    private String name;
    private String description;
    private String openingHours;
    private Long companyId;
    private Long locationId;
    private List<Long> elementInWarehouses;
    private List<Long> tools;
}
