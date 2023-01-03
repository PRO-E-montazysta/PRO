package com.emontazysta.controller;

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
        return warehouseService.getAll();
    }

    @GetMapping("{id}")
    public Warehouse getWarehouse(@PathVariable("id")Long id) {
        return warehouseService.getById(id);
    }

    @PostMapping
    public void addWarehouse(@RequestBody Warehouse warehouse) {
        warehouseService.add(warehouse);
    }

    @DeleteMapping("{id}")
    public void deleteWarehouse(@PathVariable("id") Long id) {
        warehouseService.delete(id);
    }

    @PutMapping("{id}")
    public void updateWarehouse(@PathVariable("id") Long id,
                                @RequestBody Warehouse warehouse) {
        warehouseService.update(id, warehouse);
    }
}
