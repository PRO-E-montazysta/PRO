package com.emontazysta.mapper;

import com.emontazysta.model.WarehouseManager;
import com.emontazysta.model.dto.WarehouseManagerDto;

public class WarehouseManagerMapper {

    public static WarehouseManagerDto toDto(WarehouseManager warehouseManager) {

        return WarehouseManagerDto.builder()
                .id(warehouseManager.getId())
                .firstName(warehouseManager.getFirstName())
                .lastName(warehouseManager.getLastName())
                .username(warehouseManager.getUsername())
                .email(warehouseManager.getEmail())
                .phone(warehouseManager.getPhone())
                .pesel(warehouseManager.getPesel())
                .build();
    }
}
