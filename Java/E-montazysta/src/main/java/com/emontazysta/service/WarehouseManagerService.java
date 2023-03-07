package com.emontazysta.service;

import com.emontazysta.model.dto.WarehouseManagerDto;

import java.util.List;

public interface WarehouseManagerService {

    List<WarehouseManagerDto> getAll();
    WarehouseManagerDto getById(Long id);
    WarehouseManagerDto add(WarehouseManagerDto warehouseManager);
    void delete(Long id);
    WarehouseManagerDto update(Long id, WarehouseManagerDto warehouseManager);
}
