package com.emontazysta.service;

import com.emontazysta.model.Warehouse;
import java.util.List;

public interface WarehouseService {

    List<Warehouse> getAll();
    Warehouse getById(Long id);
    void add(Warehouse warehouse);
    void delete(Long id);
    void update(Long id, Warehouse warehouse);
}
