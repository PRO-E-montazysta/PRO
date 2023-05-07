package com.emontazysta.mapper;

import com.emontazysta.model.ElementInWarehouse;
import com.emontazysta.model.Tool;
import com.emontazysta.model.Warehouse;
import com.emontazysta.model.dto.WarehouseDto;
import com.emontazysta.repository.CompanyRepository;
import com.emontazysta.repository.ElementInWarehouseRepository;
import com.emontazysta.repository.LocationRepository;
import com.emontazysta.repository.ToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WarehouseMapper {

    private final CompanyRepository companyRepository;
    private final LocationRepository locationRepository;
    private final ElementInWarehouseRepository elementInWarehouseRepository;
    private final ToolRepository toolRepository;

    public WarehouseDto toDto(Warehouse warehouse) {
        return WarehouseDto.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .description(warehouse.getDescription())
                .openingHours(warehouse.getOpeningHours())
                .companyId(warehouse.getCompany() == null ? null : warehouse.getCompany().getId())
                .locationId(warehouse.getLocation() == null ? null : warehouse.getLocation().getId())
                .elementInWarehouses(warehouse.getElementInWarehouses().stream()
                        .map(ElementInWarehouse::getId)
                        .collect(Collectors.toList()))
                .tools(warehouse.getTools().stream()
                        .map(Tool::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    public Warehouse toEntity(WarehouseDto warehouseDto) {

        List<ElementInWarehouse> elementInWarehouseList = new ArrayList<>();
        warehouseDto.getElementInWarehouses().forEach(elementInWarehouseId -> elementInWarehouseList.add(elementInWarehouseRepository.findById(elementInWarehouseId).orElseThrow(EntityNotFoundException::new)));

        List<Tool> toolList = new ArrayList<>();
        warehouseDto.getTools().forEach(toolId -> toolList.add(toolRepository.findById(toolId).orElseThrow(EntityNotFoundException::new)));

        return Warehouse.builder()
                .id(warehouseDto.getId())
                .name(warehouseDto.getName())
                .description(warehouseDto.getDescription())
                .openingHours(warehouseDto.getOpeningHours())
                .company(warehouseDto.getCompanyId() == null ? null : companyRepository.findById(warehouseDto.getCompanyId()).orElseThrow(EntityNotFoundException::new))
                .location(warehouseDto.getLocationId() == null ? null : locationRepository.findById(warehouseDto.getLocationId()).orElseThrow(EntityNotFoundException::new))
                .elementInWarehouses(elementInWarehouseList)
                .tools(toolList)
                .build();
    }
}
