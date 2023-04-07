package com.emontazysta.controller;

import com.emontazysta.model.dto.CompanyAdminDto;
import com.emontazysta.service.CompanyAdminService;
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
@RequestMapping(value = API_BASE_CONSTANT + "/company-admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyAdminController {

    private final CompanyAdminService companyAdminService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Company Admins.", security = @SecurityRequirement(name = "bearer-key"))
    public List<CompanyAdminDto> getAllCompanyAdmins() {
        return companyAdminService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Company Admin by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public CompanyAdminDto getCompanyAdminById(@PathVariable Long id) {
        return companyAdminService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Company Admin.", security = @SecurityRequirement(name = "bearer-key"))
    public CompanyAdminDto addCompanyAdmin(@Valid @RequestBody CompanyAdminDto companyAdmin) {
        return companyAdminService.add(companyAdmin);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Company Admin by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteCompanyAdminById(@PathVariable Long id) {
        companyAdminService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Company Admin by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public CompanyAdminDto updateCompanyAdmin(@PathVariable Long id, @Valid @RequestBody CompanyAdminDto companyAdmin) {
        return companyAdminService.update(id, companyAdmin);
    }
}
