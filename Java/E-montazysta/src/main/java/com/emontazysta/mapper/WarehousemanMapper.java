package com.emontazysta.mapper;

import com.emontazysta.model.Warehouseman;
import com.emontazysta.model.dto.WarehousemanDto;

public class WarehousemanMapper {

    public static WarehousemanDto toDto(Warehouseman warehouseman) {

        return WarehousemanDto.builder()
                .id(warehouseman.getId())
                .firstName(warehouseman.getFirstName())
                .lastName(warehouseman.getLastName())
                .username(warehouseman.getUsername())
                .email(warehouseman.getEmail())
                .phone(warehouseman.getPhone())
                .pesel(warehouseman.getPesel())
                .build();
    }
}
