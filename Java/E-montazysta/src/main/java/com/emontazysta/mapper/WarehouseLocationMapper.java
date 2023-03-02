package com.emontazysta.mapper;

import com.emontazysta.model.Warehouse;
import com.emontazysta.model.dto.WarehouseLocationDto;
import com.emontazysta.repository.ElementInWarehouseRepository;
import com.emontazysta.repository.LocationRepository;
import com.emontazysta.repository.ToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WarehouseLocationMapper {

    private final LocationRepository locationRepository;
    private final ElementInWarehouseRepository elementInWarehouseRepository;
    private final ToolRepository toolRepository;

    public WarehouseLocationDto toDto(Warehouse warehouse) {
        return WarehouseLocationDto.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .description(warehouse.getDescription())
                .openingHours(warehouse.getOpeningHours())
                .city(warehouse.getLocation().getCity())
                .street(warehouse.getLocation().getStreet())
                .propertyNumber(warehouse.getLocation().getPropertyNumber())
                .apartmentNumber(warehouse.getLocation().getApartmentNumber())
                .zipCode(warehouse.getLocation().getZipCode())
                .build();
    }
}
