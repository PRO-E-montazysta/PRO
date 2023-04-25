package com.emontazysta.service;

import com.emontazysta.model.dto.CompanyAdminDto;

import java.util.List;

public interface CompanyAdminService {

    List<CompanyAdminDto> getAll();
    CompanyAdminDto getById(Long id);
    CompanyAdminDto add(CompanyAdminDto companyAdminDto);
    void delete(Long id);
    CompanyAdminDto update(Long id, CompanyAdminDto companyAdminDto);
}
