package com.emontazysta.controller;

import com.emontazysta.mapper.WarehouseMapper;
import com.emontazysta.model.Warehouse;
import com.emontazysta.model.dto.WarehouseDto;
import com.emontazysta.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@AllArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/warehouses", produces = MediaType.APPLICATION_JSON_VALUE)
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Warehouses.", security = @SecurityRequirement(name = "bearer-key"))
    public List<WarehouseDto> getAll() {
        /*return ResponseEntity.ok().body(warehouseService.getAll().stream()
                .map(WarehouseMapper::warehouseToDto)
                .collect(Collectors.toList()));*/
        return warehouseService.getAll().stream()
                .map(WarehouseMapper::warehouseToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Warehouse by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public WarehouseDto getById(@PathVariable("id")Long id) {
        return WarehouseMapper.warehouseToDto(warehouseService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Warehouse.", security = @SecurityRequirement(name = "bearer-key"))
    public void add(@Valid @RequestBody Warehouse warehouse) {
        warehouseService.add(warehouse);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Warehouse by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void delete(@PathVariable("id") Long id) {
        warehouseService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Warehouse by given Id, and Warehouse.", security = @SecurityRequirement(name = "bearer-key"))
    public void update(@PathVariable("id") Long id,
                                @Valid @RequestBody Warehouse warehouse) {
        warehouseService.update(id, warehouse);
    }
}
