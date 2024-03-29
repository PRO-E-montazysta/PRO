package com.emontazysta.controller;


import com.emontazysta.model.dto.ToolTypeDto;
import com.emontazysta.model.searchcriteria.ToolTypeSearchCriteria;
import com.emontazysta.service.ToolTypeService;
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
@RequestMapping(value = API_BASE_CONSTANT + "/tooltype", produces = MediaType.APPLICATION_JSON_VALUE)
public class ToolTypeController {

    private final ToolTypeService toolTypeService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all tool types.", security = @SecurityRequirement(name = "bearer-key"))
    public List<ToolTypeDto> getAllToolTypes() {
        return  toolTypeService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get tool type by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ToolTypeDto getToolTypeById(@PathVariable Long id) {
        return toolTypeService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new tool type.", security = @SecurityRequirement(name = "bearer-key"))
    public ToolTypeDto addToolType(@Valid @RequestBody ToolTypeDto toolType) {
        return toolTypeService.add(toolType);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete tool type by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteToolTypeById(@PathVariable Long id) {
        toolTypeService.delete(id);
    }


    @GetMapping("/filter")
    @Operation(description = "Allows to get filtered tooltypes.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<ToolTypeDto>> getfilteredTools(ToolTypeSearchCriteria toolTypeSearchCriteria){
        return new ResponseEntity<>(toolTypeService.findAllWithFilters(toolTypeSearchCriteria),HttpStatus.OK);}

    @PutMapping("/{id}")
    @Operation(description = "Allows to delete tool type by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ToolTypeDto updateToolType(@PathVariable Long id, @Valid @RequestBody ToolTypeDto toolType) {
        return toolTypeService.update(id, toolType);
    }

}
