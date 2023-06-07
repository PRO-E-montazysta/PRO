package com.emontazysta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

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
    private String city;
    private String street;
    private String propertyNumber;
    private String apartmentNumber;
    private String zipCode;
    private Long orderId;
    private Long warehouseId;
    private boolean deleted;
}
