package com.emontazysta.controller;

import com.emontazysta.model.dto.WarehouseManagerDto;
import com.emontazysta.service.WarehouseManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/warehouse-managers", produces = MediaType.APPLICATION_JSON_VALUE)
public class WarehouseManagerController {

    private final WarehouseManagerService warehouseManagerService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Warehouse Managers.", security = @SecurityRequirement(name = "bearer-key"))
    public List<WarehouseManagerDto> getAllWarehouseManagers(Principal principal) {
        return warehouseManagerService.getAll(principal);
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to Warehouse Manager by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public WarehouseManagerDto getWarehouseManagerById(@PathVariable Long id) {
        return warehouseManagerService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Warehouse Manager.", security = @SecurityRequirement(name = "bearer-key"))
    public WarehouseManagerDto addWarehouseManager(@Valid @RequestBody WarehouseManagerDto warehouseManager) {
        return warehouseManagerService.add(warehouseManager);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Warehouse Manager by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteWarehouseManagerById(@PathVariable Long id) {
        warehouseManagerService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Warehouse Manager by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public WarehouseManagerDto updateWarehouseManager(@PathVariable Long id, @Valid @RequestBody WarehouseManagerDto warehouseManager) {
        return warehouseManagerService.update(id, warehouseManager);
    }
}
