package com.emontazysta.controller;

import com.emontazysta.mapper.SalesRepresentativeMapper;
import com.emontazysta.model.SalesRepresentative;
import com.emontazysta.model.dto.SalesRepresentativeDto;
import com.emontazysta.service.SalesRepresentativeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/sales-representatives", produces = MediaType.APPLICATION_JSON_VALUE)
public class SalesRepresentativeController {

    private final SalesRepresentativeService salesRepresentativeService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Sales Representatives.", security = @SecurityRequirement(name = "bearer-key"))
    public List<SalesRepresentativeDto> getAllSalesRepresentatives() {
        return salesRepresentativeService.getAll().stream()
                .map(SalesRepresentativeMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Sales Representative by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public SalesRepresentativeDto getSalesRepresentativeById(@PathVariable Long id) {
        return SalesRepresentativeMapper.toDto(salesRepresentativeService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Sales Representative.", security = @SecurityRequirement(name = "bearer-key"))
    public void addSalesRepresentative(@Valid @RequestBody SalesRepresentative salesRepresentative) {
        salesRepresentativeService.add(salesRepresentative);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Sales Representative by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteSalesRepresentativeById(@PathVariable Long id) {
        salesRepresentativeService.delete(id);
    }
}
