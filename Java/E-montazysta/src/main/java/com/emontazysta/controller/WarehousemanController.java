package com.emontazysta.controller;

import com.emontazysta.mapper.WarehousemanMapper;
import com.emontazysta.model.Warehouseman;
import com.emontazysta.model.dto.WarehousemanDto;
import com.emontazysta.service.WarehousemanService;
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
@RequestMapping(value = API_BASE_CONSTANT + "/warehousemen", produces = MediaType.APPLICATION_JSON_VALUE)
public class WarehousemanController {

    private final WarehousemanService warehousemanService;

    @GetMapping
    @Operation(description = "Allows to get all Warehousemen.", security = @SecurityRequirement(name = "bearer-key"))
    public List<WarehousemanDto> getAllWarehousemen() {
        return warehousemanService.getAll().stream()
                .map(WarehousemanMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Operation(description = "Allows to get Warehouseman by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public WarehousemanDto getWarehousemanById(@PathVariable Long id) {
        return WarehousemanMapper.toDto(warehousemanService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Warehouseman.", security = @SecurityRequirement(name = "bearer-key"))
    public void addWarehouseman(@Valid @RequestBody Warehouseman warehouseman) {
        warehousemanService.add(warehouseman);
    }

    @DeleteMapping("{id}")
    @Operation(description = "Allows to delete Warehouseman by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteWarehousemanById(@PathVariable Long id) {
        warehousemanService.delete(id);
    }
}
