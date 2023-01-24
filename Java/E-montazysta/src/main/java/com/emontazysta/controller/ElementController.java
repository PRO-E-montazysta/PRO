package com.emontazysta.controller;

import com.emontazysta.model.Element;
import com.emontazysta.service.ElementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/elements", produces = MediaType.APPLICATION_JSON_VALUE)
public class ElementController {

    private final ElementService elementService;

    @GetMapping
    @Operation(description = "Allows to get all Elements.", security = @SecurityRequirement(name = "bearer-key"))
    public List<Element> getAll() {
        return elementService.getAll();
    }

    @GetMapping("{id}")
    @Operation(description = "Allows to get Element by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public Element getById(@PathVariable Long id) {
        return elementService.getById(id);
    }

    @GetMapping("/bycode/{code}")
    @Operation(description = "Allows to get Element by given Code.", security = @SecurityRequirement(name = "bearer-key"))
    public Element getByCode(@PathVariable String code) {
        return elementService.getByCode(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Element.", security = @SecurityRequirement(name = "bearer-key"))
    public void add(@RequestBody Element element) {
        elementService.add(element);
    }

    @DeleteMapping("{id}")
    @Operation(description = "Allows to delete Element by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteById(@PathVariable Long id) {
        elementService.delete(id);
    }

    @PutMapping("{id}")
    @Operation(description = "Allows to update Element by given Id and Element.", security = @SecurityRequirement(name = "bearer-key"))
    public void update(@PathVariable Long id, @RequestBody Element element) {
        elementService.update(id, element);
    }
}
