package com.emontazysta.controller;

import com.emontazysta.model.Company;
import com.emontazysta.service.impl.CompanyServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("company")
public class CompanyController {

    private final CompanyServiceImpl companyService;

    @GetMapping
    public List<Company> getCompanies() {
        return companyService.getAll();
    }

    @GetMapping("{companyId}")
    public Company getCompany(@PathVariable("companyId") Long companyId) {
        return companyService.getById(companyId);
    }

    @PostMapping
    public void addCompany(@RequestBody Company company) {
        companyService.add(company);
    }

    @DeleteMapping("{companyId}")
    public void deleteCompany(@PathVariable("companyId") Long companyId) {
         companyService.delete(companyId);
    }

    @PutMapping("{companyId}")
    public void updateCompany(@PathVariable("companyId") Long companyId,
                              @RequestBody Company company) {
        companyService.update(companyId, company);
    }
}
