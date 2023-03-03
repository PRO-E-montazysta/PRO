package com.emontazysta.service;

import com.emontazysta.model.dto.WarehouseDto;
import com.emontazysta.model.dto.WarehouseLocationDto;
import com.emontazysta.model.searchcriteria.WarehouseSearchCriteria;

import java.util.List;

public interface WarehouseService {

    List<WarehouseDto> getAll();
    WarehouseDto getById(Long id);
    WarehouseDto add(WarehouseDto warehouse);
    void delete(Long id);
    WarehouseDto update(Long id, WarehouseDto warehouse);
    List <WarehouseLocationDto> findAllWithFilters(WarehouseSearchCriteria warehouseSearchCriteria);
}
