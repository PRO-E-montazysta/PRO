package com.emontazysta.service;

import com.emontazysta.model.Element;
import com.emontazysta.model.dto.ElementInWarehouseDto;
import com.emontazysta.model.dto.filterDto.ElementInWarehouseFilterDto;
import com.emontazysta.model.searchcriteria.ElementInWarehouseSearchCriteria;

import java.util.List;

public interface ElementInWarehouseService {

    ElementInWarehouseDto getById(Long id);
    void delete(Long id);
    ElementInWarehouseDto update(Long id, ElementInWarehouseDto elementInWarehouse);
    void changeInWarehouseCountByQuantity(Element element, Long warehouseId, int quantity);
    List<ElementInWarehouseFilterDto> getFilteredWarehouseCount(Long elementId, ElementInWarehouseSearchCriteria elementInWarehouseSearchCriteria);
}
