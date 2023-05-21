package com.emontazysta.controller;

import com.emontazysta.model.dto.ElementInWarehouseDto;
import com.emontazysta.model.dto.filterDto.ElementInWarehouseFilterDto;
import com.emontazysta.model.searchcriteria.ElementInWarehouseSearchCriteria;
import com.emontazysta.service.ElementInWarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@AllArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/elementInWarehouse", produces = MediaType.APPLICATION_JSON_VALUE)
public class ElementInWarehouseController {

    private final ElementInWarehouseService service;

    @GetMapping("/{id}")
    @Operation(description = "Allows to get element in warehouse by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ElementInWarehouseDto getElementInWarehouseById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/element/{elementId}")
    @Operation(description = "Allows to get element in warehouse by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public List<ElementInWarehouseFilterDto> getFilteredWarehouseCount(ElementInWarehouseSearchCriteria elementInWarehouseSearchCriteria, @PathVariable Long elementId) {
        return service.getFilteredWarehouseCount(elementId, elementInWarehouseSearchCriteria);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete element from warehouse by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteElementInWarehouseById(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update element from warehouse by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ElementInWarehouseDto updateElementInWarehouse(@PathVariable Long id, @Valid @RequestBody ElementInWarehouseDto element) {
        return service.update(id, element);
    }
}
