package com.emontazysta.controller;

import com.emontazysta.mapper.ElementMapper;
import com.emontazysta.model.Element;
import com.emontazysta.model.dto.ElementDto;
import com.emontazysta.service.ElementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/elements", produces = MediaType.APPLICATION_JSON_VALUE)
public class ElementController {

    private final ElementService elementService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Elements.", security = @SecurityRequirement(name = "bearer-key"))
    public List<ElementDto> getAll() {
        return elementService.getAll().stream()
                .map(ElementMapper::elementToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Element by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ElementDto getById(@PathVariable Long id) {
        return ElementMapper.elementToDto(elementService.getById(id));
    }

    @GetMapping("/bycode/{code}")
    @Operation(description = "Allows to get Element by given Code.", security = @SecurityRequirement(name = "bearer-key"))
    public Element getByCode(@PathVariable String code) {
        return elementService.getByCode(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Element.", security = @SecurityRequirement(name = "bearer-key"))
    public void add(@Valid @RequestBody Element element) {
        elementService.add(element);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Element by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteById(@PathVariable Long id) {
        elementService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Element by given Id and Element.", security = @SecurityRequirement(name = "bearer-key"))
    public void update(@PathVariable Long id, @RequestBody Element element) {
        elementService.update(id, element);
    }
}
