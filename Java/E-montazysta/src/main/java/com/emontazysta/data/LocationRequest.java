package com.emontazysta.data;

import lombok.Data;

@Data
public class LocationRequest {

    private String name;

    private Double xCoordinate;

    private Double yCoordinate;

    private String city;

    private String street;

    private String propertyNumber;

    private String apartmentNumber;

    private String zipCode;
}
