package com.emontazysta.service;

import com.emontazysta.model.dto.WarehousemanDto;

import java.util.List;

public interface WarehousemanService {

    List<WarehousemanDto> getAll();
    WarehousemanDto getById(Long id);
    WarehousemanDto add(WarehousemanDto warehouseman);
    void delete(Long id);
}
