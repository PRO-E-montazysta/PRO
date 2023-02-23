package com.emontazysta.service.impl;

import com.emontazysta.mapper.WarehouseMapper;
import com.emontazysta.model.Warehouse;
import com.emontazysta.model.dto.WarehouseDto;
import com.emontazysta.repository.WarehouseRepository;
import com.emontazysta.service.WarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository repository;
    private final WarehouseMapper warehouseMapper;

    @Override
    public List<WarehouseDto> getAll() {
        return repository.findAll().stream()
                .map(warehouseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public WarehouseDto getById(Long id) {
        Warehouse warehouse = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return warehouseMapper.toDto(warehouse);
    }

    @Override
    public WarehouseDto add(WarehouseDto warehouseDto) {
        Warehouse warehouse = warehouseMapper.toEntity(warehouseDto);
        return warehouseMapper.toDto(repository.save(warehouse));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public WarehouseDto update(Long id, WarehouseDto warehouseDto) {

        Warehouse updatedWarehouse = warehouseMapper.toEntity(warehouseDto);
        Warehouse warehouse = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        warehouse.setName(updatedWarehouse.getName());
        warehouse.setDescription(updatedWarehouse.getDescription());
        warehouse.setOpeningHours(updatedWarehouse.getOpeningHours());
        warehouse.setCompany(updatedWarehouse.getCompany());
        warehouse.setLocation(updatedWarehouse.getLocation());
        warehouse.setElementInWarehouses(updatedWarehouse.getElementInWarehouses());
        warehouse.setTools(updatedWarehouse.getTools());

        return warehouseMapper.toDto(repository.save(warehouse));
    }
}
