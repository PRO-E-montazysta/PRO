package com.emontazysta.controller;

import com.emontazysta.model.dto.ToolHistoryDto;
import com.emontazysta.service.ToolHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@AllArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/tool-history", produces = MediaType.APPLICATION_JSON_VALUE)
public class ToolHistoryController {

    private final ToolHistoryService toolHistoryService;

    @GetMapping("/{id}")
    @Operation(description = "Allows to get history for tool by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<ToolHistoryDto>> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(toolHistoryService.getToolHistory(id));
    }
}
