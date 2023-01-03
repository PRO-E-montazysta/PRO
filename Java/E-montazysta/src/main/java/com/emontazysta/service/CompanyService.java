package com.emontazysta.service;

import com.emontazysta.model.Company;
import java.util.List;

public interface CompanyService {

    List<Company> getAll();
    Company getById(Long id);
    void add(Company company);
    void delete(Long id);
    void update(Long id, Company company);
}
