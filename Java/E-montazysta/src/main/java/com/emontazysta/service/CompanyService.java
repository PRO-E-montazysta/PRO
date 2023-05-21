package com.emontazysta.service;

import com.emontazysta.model.dto.CompanyDto;
import com.emontazysta.model.dto.CompanyWithAdminDto;
import com.emontazysta.model.searchcriteria.CompanySearchCriteria;

import java.util.List;

public interface CompanyService {

    List<CompanyDto> getAll();
    CompanyDto getById(Long id);
    CompanyDto add(CompanyDto company);
    CompanyDto addCompanyWithAdmin(CompanyWithAdminDto companyWithAdminDto);
    void delete(Long id);
    CompanyDto update(Long id, CompanyDto company);
    List<CompanyDto> getFilteredCompanies(CompanySearchCriteria companySearchCriteria);
}
