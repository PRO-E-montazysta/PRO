package com.emontazysta.service;

import com.emontazysta.model.Warehouseman;

import java.util.List;

public interface WarehousemanService {

    List<Warehouseman> getAll();
    Warehouseman getById(Long id);
    void add(Warehouseman warehouseman);
    void delete(Long id);
}
