package com.emontazysta.service;

import com.emontazysta.model.dto.ElementInWarehouseDto;
import com.emontazysta.model.dto.filterDto.ElementInWarehouseFilterDto;
import com.emontazysta.model.searchcriteria.ElementInWarehouseSearchCriteria;

import java.util.List;

public interface ElementInWarehouseService {

    List<ElementInWarehouseDto> getAll();
    ElementInWarehouseDto getById(Long id);
    ElementInWarehouseDto add(ElementInWarehouseDto elementInWarehouse);
    void delete(Long id);
    ElementInWarehouseDto update(Long id, ElementInWarehouseDto elementInWarehouse);
    List<ElementInWarehouseFilterDto> getFilteredWarehouseCount(Long elementId, ElementInWarehouseSearchCriteria elementInWarehouseSearchCriteria);
}
