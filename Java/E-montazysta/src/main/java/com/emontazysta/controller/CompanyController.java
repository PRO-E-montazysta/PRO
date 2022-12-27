package com.emontazysta.controller;

import com.emontazysta.data.CompanyRequest;
import com.emontazysta.model.Company;
import com.emontazysta.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public List<Company> getCompanies() {
        return companyService.getCompanies();
    }

    @GetMapping("{companyId}")
    public Company getCompany(@PathVariable("companyId") Long companyId) {
        return companyService.getCompany(companyId);
    }

    @PostMapping
    public void addCompany(@RequestBody CompanyRequest companyToAdd) {
        companyService.addCompany(companyToAdd);
    }

    @DeleteMapping("{companyId}")
    public void deleteCompany(@PathVariable("companyId") Long companyId) {
         companyService.deleteCompany(companyId);
    }

    @PutMapping("{companyId}")
    public void updateCompany(@PathVariable("companyId") Long companyId,
                              @RequestBody CompanyRequest companyToAdd) {
        companyService.updateCompany(companyId, companyToAdd);
    }
}
