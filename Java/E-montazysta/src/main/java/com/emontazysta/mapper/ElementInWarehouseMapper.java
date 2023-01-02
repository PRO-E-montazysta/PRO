package com.emontazysta.mapper;

import com.emontazysta.model.ElementInWarehouse;
import com.emontazysta.model.dto.ElementInWarehouseDto;

public class ElementInWarehouseMapper {
    public static ElementInWarehouseDto toDto(ElementInWarehouse elementInWarehouse){
        return ElementInWarehouseDto.builder()
                .id(elementInWarehouse.getId())
                .inWarehouseCount(elementInWarehouse.getInWarehouseCount())
                .inUnitCount(elementInWarehouse.getInUnitCount())
                .rack(elementInWarehouse.getRack())
                .shelf(elementInWarehouse.getShelf())
                .build();

    }
}
