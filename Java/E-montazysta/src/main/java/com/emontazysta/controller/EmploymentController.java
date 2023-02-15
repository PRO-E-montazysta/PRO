package com.emontazysta.controller;

import com.emontazysta.mapper.EmploymentMapper;
import com.emontazysta.model.Attachment;
import com.emontazysta.model.Employment;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.service.EmploymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/employments", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmploymentController {

    private final EmploymentService employmentService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Employments.", security = @SecurityRequirement(name = "bearer-key"))
    public List<EmploymentDto> getAll() {
        return employmentService.getAll().stream()
                .map(EmploymentMapper::employmentToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Employment by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public EmploymentDto getById(@PathVariable Long id) {
        return EmploymentMapper.employmentToDto(employmentService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Employment.", security = @SecurityRequirement(name = "bearer-key"))
    public void add(@Valid @RequestBody Employment employment) {
        employmentService.add(employment);
    }
}
