package com.emontazysta.service.impl;

import com.emontazysta.model.Company;
import com.emontazysta.repository.CompanyRepository;
import com.emontazysta.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository repository;

    @Override
    public List<Company> getAll() {
        return repository.findAll();
    }

    @Override
    public Company getById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void add(Company company) {
        company.setCreatedAt(new Date());

        repository.save(company);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Long id, Company company) {
        Company updatedCompany = this.getById(id);
        updatedCompany.setCompanyName(company.getCompanyName());
        repository.save(updatedCompany);
    }
}
