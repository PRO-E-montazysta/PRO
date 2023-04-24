package com.emontazysta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseWithLocationDto {
    private Long warehouseId;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private String description;
    @NotBlank(message = "Opening hours cannot be empty")
    private String openingHours;
    private Long companyId;
    private Long locationId;
    private Double xCoordinate;
    private Double yCoordinate;
    @NotBlank(message = "City cannot be empty")
    private String city;
    @NotBlank(message = "Street cannot be empty")
    private String street;
    @NotBlank(message = "Property number cannot be empty")
    private String propertyNumber;
    @NotBlank(message = "Apartment number cannot be empty")
    private String apartmentNumber;
    @NotBlank(message = "Zip code cannot be empty")
    private String zipCode;
}
