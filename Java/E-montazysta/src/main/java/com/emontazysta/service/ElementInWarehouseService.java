package com.emontazysta.service;

import com.emontazysta.model.ElementInWarehouse;

import java.util.List;

public interface ElementInWarehouseService {

    List<ElementInWarehouse> getAll();
    ElementInWarehouse getById(Long id);
    void add(ElementInWarehouse elementInWarehouse);
    void delete(Long id);
}
