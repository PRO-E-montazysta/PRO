package com.emontazysta.service;

import com.emontazysta.model.WarehouseManager;

import java.util.List;

public interface WarehouseManagerService {

    List<WarehouseManager> getAll();
    WarehouseManager getById(Long id);
    void add(WarehouseManager warehouseManager);
    void delete(Long id);
}
