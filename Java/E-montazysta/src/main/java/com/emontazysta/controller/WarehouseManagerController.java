package com.emontazysta.controller;

import com.emontazysta.mapper.WarehouseManagerMapper;
import com.emontazysta.model.WarehouseManager;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/warehouse-managers", produces = MediaType.APPLICATION_JSON_VALUE)
public class WarehouseManagerController {

    private final WarehouseManagerService warehouseManagerService;

    @GetMapping
    @Operation(description = "Allows to get all Warehouse Managers.", security = @SecurityRequirement(name = "bearer-key"))
    public List<WarehouseManagerDto> getAllWarehouseManagers() {
        return warehouseManagerService.getAll().stream()
                .map(WarehouseManagerMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Operation(description = "Allows to Warehouse Manager by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public WarehouseManagerDto getWarehouseManagerById(@PathVariable Long id) {
        return WarehouseManagerMapper.toDto(warehouseManagerService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Warehouse Manager.", security = @SecurityRequirement(name = "bearer-key"))
    public void addWarehouseManager(@Valid @RequestBody WarehouseManager warehouseManager) {
        warehouseManagerService.add(warehouseManager);
    }

    @DeleteMapping("{id}")
    @Operation(description = "Allows to delete Warehouse Manager by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteWarehouseManagerById(@PathVariable Long id) {
        warehouseManagerService.delete(id);
    }
}
