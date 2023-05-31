package com.emontazysta.controller;

import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.service.EmploymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
@RequestMapping(value = API_BASE_CONSTANT + "/employments", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmploymentController {

    private final EmploymentService employmentService;

    @GetMapping("/for-employee/{employeeId}")
    @Operation(description = "Allows to get all Employments of given employee.", security = @SecurityRequirement(name = "bearer-key"))
    public List<EmploymentDto> getAllEmployeeEmployments(@PathVariable Long employeeId) {
        return employmentService.getAllEmployeeEmployments(employeeId);
    }

    @PostMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to hire Employee of given id.", security = @SecurityRequirement(name = "bearer-key"))
    public EmploymentDto hire(@PathVariable Long employeeId) {
        return employmentService.hire(employeeId);
    }

    @PutMapping("/{employeeId}")
    @Operation(description = "Allows to dismiss Employee of given id.", security = @SecurityRequirement(name = "bearer-key"))
    public EmploymentDto dismiss(@PathVariable Long employeeId) {
        return employmentService.dismiss(employeeId);
    }
}
