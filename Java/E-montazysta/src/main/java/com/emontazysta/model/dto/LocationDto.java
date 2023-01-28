package com.emontazysta.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class LocationDto {

    private Long id;
    private String name;
    private Double xCoordinate;
    private Double yCoordinate;
    private String city;
    private String street;
    private String propertyNumber;
    private String apartmentNumber;
    private String zipCode;
}
