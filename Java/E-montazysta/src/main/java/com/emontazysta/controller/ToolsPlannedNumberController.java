package com.emontazysta.controller;

import com.emontazysta.model.dto.ToolsPlannedNumberDto;
import com.emontazysta.service.ToolsPlannedNumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/tools-planned", produces = MediaType.APPLICATION_JSON_VALUE)
public class ToolsPlannedNumberController {

    private final ToolsPlannedNumberService toolsPlannedNumberService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all planed number of tools.", security = @SecurityRequirement(name = "bearer-key"))
    public List<ToolsPlannedNumberDto> getAllToolsPlannedNumber() {
        return toolsPlannedNumberService.getAll();
    }

    @GetMapping("{id}")
    @Operation(description = "Allows to get planed number of tools by id.", security = @SecurityRequirement(name = "bearer-key"))
    public ToolsPlannedNumberDto getToolsPlannedNumberDtoById(@PathVariable Long id) {
        return toolsPlannedNumberService.getById(id);
    }
}
