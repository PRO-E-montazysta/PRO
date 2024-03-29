package com.emontazysta.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/sales-representatives", produces = MediaType.APPLICATION_JSON_VALUE)
public class SalesRepresentativeController {

    private final SalesRepresentativeService salesRepresentativeService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Sales Representatives.", security = @SecurityRequirement(name = "bearer-key"))
    public List<SalesRepresentativeDto> getAllSalesRepresentatives(Principal principal) {
        return salesRepresentativeService.getAll(principal);
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Sales Representative by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public SalesRepresentativeDto getSalesRepresentativeById(@PathVariable Long id) {
        return salesRepresentativeService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Sales Representative.", security = @SecurityRequirement(name = "bearer-key"))
    public SalesRepresentativeDto addSalesRepresentative(@Valid @RequestBody SalesRepresentativeDto salesRepresentative) {
        return salesRepresentativeService.add(salesRepresentative);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Sales Representative by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteSalesRepresentativeById(@PathVariable Long id) {
        salesRepresentativeService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Sales Representative by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public SalesRepresentativeDto updateSalesRepresentative(@PathVariable Long id, @Valid @RequestBody SalesRepresentativeDto salesRepresentative) {
        return salesRepresentativeService.update(id, salesRepresentative);
    }
}
