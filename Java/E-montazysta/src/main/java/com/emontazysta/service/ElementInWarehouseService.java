package com.emontazysta.service;

import com.emontazysta.model.dto.ElementInWarehouseDto;

import java.util.List;

public interface ElementInWarehouseService {

    List<ElementInWarehouseDto> getAll();
    ElementInWarehouseDto getById(Long id);
    ElementInWarehouseDto add(ElementInWarehouseDto elementInWarehouse);
    void delete(Long id);
}
