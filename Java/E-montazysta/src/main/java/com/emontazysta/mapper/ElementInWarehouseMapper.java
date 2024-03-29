package com.emontazysta.mapper;

import com.emontazysta.model.ElementInWarehouse;
import com.emontazysta.model.dto.ElementInWarehouseDto;
import com.emontazysta.model.dto.filterDto.ElementInWarehouseFilterDto;
import com.emontazysta.repository.ElementRepository;
import com.emontazysta.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class ElementInWarehouseMapper {

    private final ElementRepository elementRepository;
    private final WarehouseRepository warehouseRepository;

    public ElementInWarehouseDto toDto(ElementInWarehouse elementInWarehouse) {
        return ElementInWarehouseDto.builder()
                .id(elementInWarehouse.getId())
                .inWarehouseCount(elementInWarehouse.getInWarehouseCount())
                .rack(elementInWarehouse.getRack())
                .shelf(elementInWarehouse.getShelf())
                .elementId(elementInWarehouse.getElement() == null ? null : elementInWarehouse.getElement().getId())
                .warehouseId(elementInWarehouse.getWarehouse() == null ? null : elementInWarehouse.getWarehouse().getId())
                .build();
    }

    public ElementInWarehouseFilterDto toFilterDto(ElementInWarehouse elementInWarehouse) {
        return ElementInWarehouseFilterDto.builder()
                .id(elementInWarehouse.getId())
                .inWarehouseCount(elementInWarehouse.getInWarehouseCount())
                .rack(elementInWarehouse.getRack())
                .shelf(elementInWarehouse.getShelf())
                .element(elementInWarehouse.getElement() == null ? null : elementInWarehouse.getElement().getName())
                .warehouse(elementInWarehouse.getWarehouse() == null ? null : elementInWarehouse.getWarehouse().getName())
                .build();
    }

    public ElementInWarehouse toEntity(ElementInWarehouseDto elementInWarehouseDto) {
        return ElementInWarehouse.builder()
                .id(elementInWarehouseDto.getId())
                .inWarehouseCount(elementInWarehouseDto.getInWarehouseCount())
                .rack(elementInWarehouseDto.getRack())
                .shelf(elementInWarehouseDto.getShelf())
                .element(elementInWarehouseDto.getElementId() == null ? null : elementRepository.findById(elementInWarehouseDto.getElementId()).orElseThrow(EntityNotFoundException::new))
                .warehouse(elementInWarehouseDto.getWarehouseId() == null ? null : warehouseRepository.findById(elementInWarehouseDto.getWarehouseId()).orElseThrow(EntityNotFoundException::new))
                .build();
    }
}
