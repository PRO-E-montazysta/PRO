package com.emontazysta.model.dto;

import com.emontazysta.model.Company;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WarehouseDto {

    private Long id;
    private String name;
    private String description;
    private String openingHours;
    private Company company;
}
