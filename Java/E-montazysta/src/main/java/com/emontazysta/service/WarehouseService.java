package com.emontazysta.service;

import com.emontazysta.data.WarehouseRequest;
import com.emontazysta.model.Warehouse;
import com.emontazysta.repositoriy.WarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public List<Warehouse> getWarehouses() {
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouse(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse with id " + id + " not found!"));
    }

    public void addWarehouse(WarehouseRequest warehouseToAdd) {
        Warehouse newWarehouse = new Warehouse();
        newWarehouse.setName(warehouseToAdd.getName());
        newWarehouse.setDescription(warehouseToAdd.getDescription());
        newWarehouse.setOpeningHours(warehouseToAdd.getOpeningHours());

        warehouseRepository.save(newWarehouse);
    }

    public void deleteWarehouse(Long id) {
        warehouseRepository.deleteById(id);
    }

    public void updateWarehouse(Long id, WarehouseRequest warehouseToUpdate) {
        Warehouse updatedWarehouse = new Warehouse();
        updatedWarehouse.setName(warehouseToUpdate.getName());
        updatedWarehouse.setDescription(warehouseToUpdate.getDescription());
        updatedWarehouse.setOpeningHours(warehouseToUpdate.getOpeningHours());

        warehouseRepository.save(updatedWarehouse);
    }
}
