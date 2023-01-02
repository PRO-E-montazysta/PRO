package com.emontazysta.controller;

import com.emontazysta.model.Tool;
import com.emontazysta.service.impl.ToolServiceImpl;
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
@RequestMapping(value = API_BASE_CONSTANT + "/tools", produces = MediaType.APPLICATION_JSON_VALUE)
public class ToolController {

    private final ToolServiceImpl toolService;

    @GetMapping
    @Operation(description = "Allows to get all Tools.", security = @SecurityRequirement(name = "bearer-key"))
    public List<Tool> getTools() {
        return toolService.getAll();
    }

    @GetMapping("{id}")
    @Operation(description = "Allows to get Tool by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public Tool getById(@PathVariable Long id) {
        return toolService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Tool.", security = @SecurityRequirement(name = "bearer-key"))
    public void add(@RequestBody Tool tool) {
        toolService.add(tool);
    }

    @DeleteMapping("{id}")
    @Operation(description = "Allows to delete Tool by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteById(@PathVariable Long id) {
        toolService.delete(id);
    }

    @PutMapping("{id}")
    @Operation(description = "Allows to edit Tool by given Id and Tool data.", security = @SecurityRequirement(name = "bearer-key"))
    public void update(@PathVariable Long id, @RequestBody Tool tool) {
        toolService.update(id, tool);
    }
}