package com.emontazysta.controller;

import com.emontazysta.data.WarehouseRequest;
import com.emontazysta.model.Warehouse;
import com.emontazysta.service.WarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    public List<Warehouse> getWarehouses() {
        return warehouseService.getWarehouses();
    }

    @GetMapping
    public Warehouse getWarehouse(Long id) {
        return warehouseService.getWarehouse(id);
    }

    @PostMapping
    public void addWarehouse(@RequestBody WarehouseRequest warehouseToAdd) {
        warehouseService.addWarehouse(warehouseToAdd);
    }

    @DeleteMapping("{warehouseId}")
    public void deleteWarehouse(@PathVariable("warehouseId") Long id) {
        warehouseService.deleteWarehouse(id);
    }

    @PutMapping("{warehouseId}")
    public void updateWarehouse(@PathVariable("warehouseId") Long id,
                                @RequestBody WarehouseRequest warehouseToUpdate) {
        warehouseService.updateWarehouse(id, warehouseToUpdate);
    }
}
