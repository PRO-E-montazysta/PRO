package com.emontazysta.controller;

import com.emontazysta.model.dto.ToolDto;
import com.emontazysta.model.dto.filterDto.ToolFilterDto;
import com.emontazysta.model.searchcriteria.ToolSearchCriteria;
import com.emontazysta.service.ToolService;
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
@RequestMapping(value = API_BASE_CONSTANT + "/tools", produces = MediaType.APPLICATION_JSON_VALUE)
public class ToolController {

    private final ToolService toolService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Tools.", security = @SecurityRequirement(name = "bearer-key"))
    public List<ToolDto> getAll() {
        return toolService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Tool by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ToolDto getById(@PathVariable Long id) {
        return toolService.getById(id);
    }

    @GetMapping("/bycode/{code}")
    @Operation(description = "Allows to get Tool by given Code.", security = @SecurityRequirement(name = "bearer-key"))
    public ToolDto getByCode(@PathVariable String code) {
        return toolService.getByCode(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Tool.", security = @SecurityRequirement(name = "bearer-key"))
    public ToolDto add(@Valid @RequestBody ToolDto tool) {
        return toolService.add(tool);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Tool by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteById(@PathVariable Long id) {
        toolService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to edit Tool by given Id and Tool data.", security = @SecurityRequirement(name = "bearer-key"))
    public ToolDto update(@PathVariable Long id, @Valid @RequestBody ToolDto tool) {
        return toolService.update(id, tool);
    }

    @GetMapping("/filter")
    @Operation(description = "Allows to get filtered tools data.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<ToolFilterDto>> getfilteredTools(ToolSearchCriteria toolSearchCriteria){
        return new ResponseEntity<>(toolService.getTools(toolSearchCriteria),HttpStatus.OK);
    }

    @GetMapping("/tools-from-warehouse/{id}")
    @Operation(description = "Allows to get filtered tools data.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<ToolFilterDto>> getfilteredToolsFromWarehouse(@PathVariable String id,
                                     ToolSearchCriteria toolSearchCriteria){
        toolSearchCriteria.setWarehouse_Id(List.of(id));
        return new ResponseEntity<>(toolService.getTools(toolSearchCriteria),HttpStatus.OK);
    }

}