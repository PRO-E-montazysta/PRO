package com.emontazysta.service;

import com.emontazysta.model.dto.WarehousemanDto;

import java.security.Principal;
import java.util.List;

public interface WarehousemanService {

    List<WarehousemanDto> getAll(Principal principal);
    WarehousemanDto getById(Long id);
    WarehousemanDto add(WarehousemanDto warehouseman);
    void delete(Long id);
    WarehousemanDto update(Long id, WarehousemanDto warehouseman);
}
