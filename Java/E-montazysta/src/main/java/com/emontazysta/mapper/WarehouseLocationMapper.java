package com.emontazysta.mapper;

import com.emontazysta.model.Location;
import com.emontazysta.model.Warehouse;
import com.emontazysta.model.dto.WarehouseLocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WarehouseLocationMapper {

    public WarehouseLocationDto toDto(Warehouse warehouse) {
        return WarehouseLocationDto.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .description(warehouse.getDescription())
                .openingHours(warehouse.getOpeningHours())
                .fullAddress(warehouse.getLocation() != null ? createFullAddress(warehouse.getLocation()) : "")
                .build();
    }

    private String createFullAddress(Location location) {
        String fullAddres = "";

        if(location.getStreet() != null) {
            fullAddres += location.getStreet();
            fullAddres += location.getPropertyNumber() != null ? (" " + location.getPropertyNumber()) : "";
            fullAddres += (location.getApartmentNumber() != null && !location.getApartmentNumber().equals(""))? ("/" + location.getApartmentNumber()) : "";
        }
        fullAddres += location.getZipCode() != null ? (", " + location.getZipCode()) : "";
        fullAddres += location.getCity() != null ? (" " + location.getCity()) : "";

        return fullAddres;
    }
}
