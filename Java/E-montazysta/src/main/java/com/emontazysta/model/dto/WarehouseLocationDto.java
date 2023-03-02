package com.emontazysta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseLocationDto {

    private Long id;
    private String name;
    private String description;
    private String openingHours;
    private String city;
    private String street;
    private String propertyNumber;
    private String apartmentNumber;
    private String zipCode;
}
