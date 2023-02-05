package com.emontazysta.controller;

import com.emontazysta.mapper.ToolReleaseMapper;
import com.emontazysta.model.ToolRelease;
import com.emontazysta.model.dto.ToolReleaseDto;
import com.emontazysta.service.ToolReleaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/tool-releases", produces = MediaType.APPLICATION_JSON_VALUE)
public class ToolReleaseController {

    private final ToolReleaseService toolReleaseService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Tool Releases.", security = @SecurityRequirement(name = "bearer-key"))
    public List<ToolReleaseDto> getAllToolReleases() {
        return toolReleaseService.getAll().stream()
                .map(ToolReleaseMapper::toolReleaseToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Tool Release by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ToolReleaseDto getToolReleaseById(@PathVariable Long id) {
        return ToolReleaseMapper.toolReleaseToDto(toolReleaseService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Tool Release.", security = @SecurityRequirement(name = "bearer-key"))
    public void addToolRelease(@RequestBody ToolRelease toolRelease) {
        toolReleaseService.add(toolRelease);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Tool Release by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteToolReleaseById(@PathVariable Long id) {
        toolReleaseService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Tool Release by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ToolRelease updateToolRelease(@PathVariable Long id, @RequestBody ToolRelease toolRelease) {
        return toolReleaseService.update(id, toolRelease);
    }
}
