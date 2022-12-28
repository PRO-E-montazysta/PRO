package com.emontazysta.controller;

import com.emontazysta.mapper.WarehouseManagerMapper;
import com.emontazysta.model.WarehouseManager;
import com.emontazysta.model.dto.WarehouseManagerDto;
import com.emontazysta.service.WarehouseManagerService;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/warehouse-managers", produces = MediaType.APPLICATION_JSON_VALUE)
public class WarehouseManagerController {

    private final WarehouseManagerService warehouseManagerService;

    @GetMapping
    public List<WarehouseManagerDto> getAllWarehouseManagers() {
        return warehouseManagerService.getAll().stream()
                .map(WarehouseManagerMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public WarehouseManagerDto getWarehouseManagerById(@PathVariable Long id) {
        return WarehouseManagerMapper.toDto(warehouseManagerService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addWarehouseManager(@RequestBody WarehouseManager warehouseManager) {
        warehouseManagerService.add(warehouseManager);
    }

    @DeleteMapping("{id}")
    public void deleteWarehouseManagerById(@PathVariable Long id) {
        warehouseManagerService.delete(id);
    }
}
