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

import javax.validation.Valid;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
@RequestMapping(value = API_BASE_CONSTANT + "/employments", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmploymentController {

    private final EmploymentService employmentService;

    //TO DELETE
    @GetMapping("/all")
    @Operation(description = "Allows to get all Employments.", security = @SecurityRequirement(name = "bearer-key"))
    public List<EmploymentDto> getAll() {
        return employmentService.getAll();
    }

    @GetMapping("/for-employee/{employeeId}")
    @Operation(description = "Allows to get all Employments of given employee.", security = @SecurityRequirement(name = "bearer-key"))
    public List<EmploymentDto> getAllEmployeeEmployments(@PathVariable Long employeeId) {
        return employmentService.getAllEmployeeEmployments(employeeId);
    }
    @PutMapping("/{employeeId}")
    @Operation(description = "Allows to dismiss Employee of given id.", security = @SecurityRequirement(name = "bearer-key"))
    public EmploymentDto dismiss(@PathVariable Long employeeId) {
        return employmentService.dismiss(employeeId);
    }

    //TO DELETE
    @GetMapping("/{id}")
    @Operation(description = "Allows to get Employment by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public EmploymentDto getById(@PathVariable Long id) {
        return employmentService.getById(id);
    }

    //TO DELETE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Employment.", security = @SecurityRequirement(name = "bearer-key"))
    public EmploymentDto add(@Valid @RequestBody EmploymentDto employment) {
        return employmentService.add(employment);
    }

    //TO DELETE
    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete new Employment.", security = @SecurityRequirement(name = "bearer-key"))
    public void delete(@PathVariable Long id) {
        employmentService.delete(id);
    }

    //TO DELETE
    @PutMapping("/{id}")
    @Operation(description = "Allows to update new Employment.", security = @SecurityRequirement(name = "bearer-key"))
    public EmploymentDto update(@PathVariable Long id, @Valid @RequestBody EmploymentDto employment) {
        return employmentService.update(id, employment);
    }
}
