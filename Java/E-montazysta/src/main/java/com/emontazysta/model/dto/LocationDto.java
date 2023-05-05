package com.emontazysta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {

    private Long id;
    @NotNull(message = "X coordinate cannot be empty")
    private Double xCoordinate;
    @NotNull(message = "Y coordinate cannot be empty")
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
    private Long orderId;
    private Long warehouseId;
}
