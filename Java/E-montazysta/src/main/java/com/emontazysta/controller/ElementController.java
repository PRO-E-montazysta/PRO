package com.emontazysta.controller;

import com.emontazysta.model.dto.ElementDto;
import com.emontazysta.model.searchcriteria.ElementSearchCriteria;
import com.emontazysta.service.ElementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/elements", produces = MediaType.APPLICATION_JSON_VALUE)
public class ElementController {

    private final ElementService elementService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Elements.", security = @SecurityRequirement(name = "bearer-key"))
    public List<ElementDto> getAll() {
        return elementService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Element by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ElementDto getById(@PathVariable Long id) {
        return elementService.getById(id);
    }

    @GetMapping("/bycode/{code}")
    @Operation(description = "Allows to get Element by given Code.", security = @SecurityRequirement(name = "bearer-key"))
    public ElementDto getByCode(@PathVariable String code) {
        return elementService.getByCode(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Element.", security = @SecurityRequirement(name = "bearer-key"))
    public ElementDto add(@Valid @RequestBody ElementDto element) {
        return elementService.addWithWarehouseCount(element);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Element by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteById(@PathVariable Long id) {
        elementService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Element by given Id and Element.", security = @SecurityRequirement(name = "bearer-key"))
    public ElementDto update(@PathVariable Long id, @Valid @RequestBody ElementDto element) {
        return elementService.update(id, element);
    }

    @GetMapping("/filter")
    @Operation(description = "Allows to get filtered elements.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<ElementDto>> filterElements(ElementSearchCriteria elementSearchCriteria){
        return new ResponseEntity<>(elementService.getFilteredElements(elementSearchCriteria), HttpStatus.OK);
    }
}
