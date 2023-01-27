package com.emontazysta.mapper;

import com.emontazysta.model.Warehouse;
import com.emontazysta.model.dto.WarehouseDto;

public class WarehouseMapper {

    public static WarehouseDto warehouseToDto(Warehouse warehouse) {
        return WarehouseDto.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .description(warehouse.getDescription())
                .openingHours(warehouse.getOpeningHours())
                .company(warehouse.getCompany())
                .build();
    }
}
