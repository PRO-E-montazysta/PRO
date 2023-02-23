package com.emontazysta.model.dto;

import com.emontazysta.model.Company;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
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
