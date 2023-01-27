package com.emontazysta.mapper;

import com.emontazysta.model.Company;
import com.emontazysta.model.dto.CompanyDto;

public class CompanyMapper {

    public static CompanyDto companyToDto(Company company) {
        return CompanyDto.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .createdAt(company.getCreatedAt())
                .warehouse(company.getWarehouses())
                .build();
    }
}
