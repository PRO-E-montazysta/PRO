package com.emontazysta.controller;

import com.emontazysta.model.Warehouse;
import com.emontazysta.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@AllArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/warehouse", produces = MediaType.APPLICATION_JSON_VALUE)
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    @Operation(description = "Allows to get all Warehouses.", security = @SecurityRequirement(name = "bearer-key"))
    public List<Warehouse> getAll() {
        return warehouseService.getAll();
    }

    @GetMapping("{id}")
    @Operation(description = "Allows to get Warehouse by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public Warehouse getById(@PathVariable("id")Long id) {
        return warehouseService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Warehouse.", security = @SecurityRequirement(name = "bearer-key"))
    public void add(@RequestBody Warehouse warehouse) {
        warehouseService.add(warehouse);
    }

    @DeleteMapping("{id}")
    @Operation(description = "Allows to delete Warehouse by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void delete(@PathVariable("id") Long id) {
        warehouseService.delete(id);
    }

    @PutMapping("{id}")
    @Operation(description = "Allows to update Warehouse by given Id, and Warehouse.", security = @SecurityRequirement(name = "bearer-key"))
    public void update(@PathVariable("id") Long id,
                                @RequestBody Warehouse warehouse) {
        warehouseService.update(id, warehouse);
    }
}
