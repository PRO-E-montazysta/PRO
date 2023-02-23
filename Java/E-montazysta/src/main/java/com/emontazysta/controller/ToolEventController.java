package com.emontazysta.controller;

import com.emontazysta.model.dto.ToolEventDto;
import com.emontazysta.service.impl.ToolEventServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/toolEvent", produces = MediaType.APPLICATION_JSON_VALUE)
public class ToolEventController {

    private final ToolEventServiceImpl service;

    @GetMapping
    @Operation(description = "Allows to get all tool event.", security = @SecurityRequirement(name = "bearer-key"))
    public List<ToolEventDto> getAllToolEvents() {
        return  service.getAll();
    }

    @GetMapping("{id}")
    @Operation(description = "Allows to get tool event by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ToolEventDto getToolEventById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new tool event.", security = @SecurityRequirement(name = "bearer-key"))
    public ToolEventDto addToolEvent(@Valid @RequestBody ToolEventDto event) {
        return service.add(event);
    }

    @DeleteMapping("{id}")
    @Operation(description = "Allows to delete tool event by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteToolEventById(@PathVariable Long id) {
        service.delete(id);
    }
}
