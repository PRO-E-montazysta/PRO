package com.emontazysta.service;

import com.emontazysta.model.Element;
import com.emontazysta.model.dto.ElementInWarehouseDto;

import java.util.List;

public interface ElementInWarehouseService {

    List<ElementInWarehouseDto> getAll();
    ElementInWarehouseDto getById(Long id);
    ElementInWarehouseDto add(ElementInWarehouseDto elementInWarehouse);
    void delete(Long id);
    ElementInWarehouseDto update(Long id, ElementInWarehouseDto elementInWarehouse);
    void changeInWarehouseCountByQuantity(Element element, Long warehouseId, int quantity);
}
