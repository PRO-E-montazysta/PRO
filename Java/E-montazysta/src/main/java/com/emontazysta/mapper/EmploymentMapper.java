package com.emontazysta.mapper;

import com.emontazysta.model.Employment;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.repository.AppUserRepository;
import com.emontazysta.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class EmploymentMapper {

    private final CompanyRepository companyRepository;
    private final AppUserRepository appUserRepository;

    public EmploymentDto toDto (Employment employment) {
        return EmploymentDto.builder()
                .id(employment.getId())
                .dateOfEmployment(employment.getDateOfEmployment())
                .dateOfDismiss(employment.getDateOfDismiss())
                .companyId(employment.getCompany() == null ? null : employment.getCompany().getId())
                .employeeId(employment.getEmployee() == null ? null : employment.getEmployee().getId())
                .deleted(employment.isDeleted())
                .build();
    }

    public Employment toEntity (EmploymentDto employmentDto) {
        return Employment.builder()
                .id(employmentDto.getId())
                .dateOfEmployment(employmentDto.getDateOfEmployment())
                .dateOfDismiss(employmentDto.getDateOfDismiss())
                .company(employmentDto.getCompanyId() == null ? null : companyRepository.findById(employmentDto.getCompanyId()).orElseThrow(EntityNotFoundException::new))
                .employee(employmentDto.getEmployeeId() == null ? null : appUserRepository.findById(employmentDto.getEmployeeId()).orElseThrow(EntityNotFoundException::new))
                .build();
    }
}
