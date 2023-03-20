package com.emontazysta.controller;

import com.emontazysta.model.dto.CompanyDto;
import com.emontazysta.model.searchcriteria.CompanySearchCriteria;
import com.emontazysta.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@AllArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_CLOUD_ADMIN')")
@RequestMapping(value = API_BASE_CONSTANT + "/companies", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Companies.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<CompanyDto>> getAll() {
        return ResponseEntity.ok().body(companyService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Company by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<CompanyDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(companyService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Company.", security = @SecurityRequirement(name = "bearer-key"))
    public CompanyDto add(@Valid @RequestBody CompanyDto company) {
        return companyService.add(company);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Company by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteById(@PathVariable("id") Long id) {
         companyService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Company by given Id, and Company.", security = @SecurityRequirement(name = "bearer-key"))
    public CompanyDto update(@PathVariable("id") Long id, @Valid @RequestBody CompanyDto company) {
        return companyService.update(id, company);
    }

    @GetMapping("/filter")
    @Operation(description = "Return filtered Companies by given parameters.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<CompanyDto>> getFiltered(CompanySearchCriteria companySearchCriteria) {
        return new ResponseEntity<>(companyService.getFilteredCompanies(companySearchCriteria),HttpStatus.OK);
    }
}
