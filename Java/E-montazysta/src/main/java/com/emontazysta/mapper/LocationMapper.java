package com.emontazysta.mapper;

import com.emontazysta.model.Location;
import com.emontazysta.model.dto.LocationDto;

public class LocationMapper {

    public static LocationDto locationToDto (Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .name(location.getName())
                .xCoordinate(location.getXCoordinate())
                .yCoordinate(location.getYCoordinate())
                .city(location.getCity())
                .street(location.getStreet())
                .propertyNumber(location.getPropertyNumber())
                .apartmentNumber(location.getApartmentNumber())
                .zipCode(location.getZipCode())
                .build();
    }
}
