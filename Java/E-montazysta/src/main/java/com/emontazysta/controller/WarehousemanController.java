package com.emontazysta.controller;

import com.emontazysta.mapper.WarehousemanMapper;
import com.emontazysta.model.Warehouseman;
import com.emontazysta.model.dto.WarehousemanDto;
import com.emontazysta.service.WarehousemanService;
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
@RequestMapping(value = "/api/warehousemen", produces = MediaType.APPLICATION_JSON_VALUE)
public class WarehousemanController {

    private final WarehousemanService warehousemanService;

    @GetMapping
    public List<WarehousemanDto> getAllWarehousemen() {
        return warehousemanService.getAll().stream()
                .map(WarehousemanMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public WarehousemanDto getWarehousemanById(@PathVariable Long id) {
        return WarehousemanMapper.toDto(warehousemanService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addWarehouseman(@RequestBody Warehouseman warehouseman) {
        warehousemanService.add(warehouseman);
    }

    @DeleteMapping("{id}")
    public void deleteWarehousemanById(@PathVariable Long id) {
        warehousemanService.delete(id);
    }
}
