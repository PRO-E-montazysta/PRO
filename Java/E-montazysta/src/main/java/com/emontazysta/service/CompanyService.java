package com.emontazysta.service;

import com.emontazysta.model.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    List<CompanyDto> getAll();
    CompanyDto getById(Long id);
    CompanyDto add(CompanyDto company);
    void delete(Long id);
    CompanyDto update(Long id, CompanyDto company);
}
