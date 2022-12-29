package com.emontazysta.service;

import com.emontazysta.data.CompanyRequest;
import com.emontazysta.model.Company;
import com.emontazysta.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompany(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company with id " + id + " not found!"));
    }

    public void addCompany(CompanyRequest companyToAdd) {
        Company newCompany = new Company();
        newCompany.setCompanyName(companyToAdd.getCompanyName());
        newCompany.setCreatedAt(new Date());

        companyRepository.save(newCompany);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    public void updateCompany(Long id, CompanyRequest companyToAdd) {
        Company updatedCompany = getCompany(id);
        updatedCompany.setCompanyName(companyToAdd.getCompanyName());

        companyRepository.save(updatedCompany);
    }
}
