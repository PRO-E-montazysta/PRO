package com.emontazysta.controller;


import com.emontazysta.mapper.ToolTypeMapper;
import com.emontazysta.model.ToolType;
import com.emontazysta.model.dto.ToolTypeDto;
import com.emontazysta.service.ToolTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/tooltype", produces = MediaType.APPLICATION_JSON_VALUE)
public class ToolTypeController {

    private final ToolTypeService toolTypeService;

    @GetMapping
    @Operation(description = "Allows to get all tool types.", security = @SecurityRequirement(name = "bearer-key"))
    public List<ToolTypeDto> getAllToolTypes() {
        return  toolTypeService.getAll().stream()
                .map(ToolTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Operation(description = "Allows to get tool type by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ToolTypeDto getToolTypeById(@PathVariable Long id) {
        return ToolTypeMapper.toDto(toolTypeService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new tool type.", security = @SecurityRequirement(name = "bearer-key"))
    public void addToolType(@RequestBody ToolType toolType) {
        toolTypeService.add(toolType);
    }

    @DeleteMapping("{id}")
    @Operation(description = "Allows to delete tool type by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteToolTypeById(@PathVariable Long id) {
        toolTypeService.delete(id);
    }
}
