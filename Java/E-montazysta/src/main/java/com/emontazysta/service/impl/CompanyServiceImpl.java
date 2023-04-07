package com.emontazysta.service.impl;

import com.emontazysta.mapper.CompanyMapper;
import com.emontazysta.mapper.CompanyWithAdminMapper;
import com.emontazysta.model.Company;
import com.emontazysta.model.CompanyAdmin;
import com.emontazysta.model.Employment;
import com.emontazysta.model.dto.CompanyDto;
import com.emontazysta.model.dto.CompanyWithAdminDto;
import com.emontazysta.model.searchcriteria.CompanySearchCriteria;
import com.emontazysta.repository.CompanyAdminRepository;
import com.emontazysta.repository.CompanyRepository;
import com.emontazysta.repository.EmploymentRepository;
import com.emontazysta.repository.criteria.CompanyCriteriaRepository;
import com.emontazysta.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyAdminRepository companyAdminRepository;
    private final EmploymentRepository employmentRepository;
    private final CompanyMapper companyMapper;
    private final CompanyCriteriaRepository companyCriteriaRepository;

    @Override
    public List<CompanyDto> getAll() {
        return companyRepository.findAll().stream()
                .map(companyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDto getById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return companyMapper.toDto(company);
    }

    @Override
    public CompanyDto add(CompanyDto companyDto) {
        Company company = companyMapper.toEntity(companyDto);
        company.setCreatedAt(LocalDate.now());

        return companyMapper.toDto(companyRepository.save(company));
    }

    @Override
    @Transactional
    public CompanyDto addCompanyWithAdmin(CompanyWithAdminDto companyWithAdminDto) {
        Company company = companyRepository.save(CompanyWithAdminMapper.companyFromDto(companyWithAdminDto));
        CompanyAdmin companyAdmin = companyAdminRepository.save(CompanyWithAdminMapper.companyAdminFromDto(companyWithAdminDto));
        employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company, companyAdmin));

        return companyMapper.toDto(company);
    }

    @Override
    public void delete(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public CompanyDto update(Long id, CompanyDto companyDto) {

        Company updatedCompany = companyMapper.toEntity(companyDto);
        Company companyToUpdate = companyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        companyToUpdate.setCompanyName(updatedCompany.getCompanyName());
        companyToUpdate.setStatus(updatedCompany.getStatus());
        companyToUpdate.setStatusReason(updatedCompany.getStatusReason());
        companyToUpdate.setWarehouses(updatedCompany.getWarehouses());
        companyToUpdate.setOrders(updatedCompany.getOrders());
        companyToUpdate.setClients(updatedCompany.getClients());
        companyToUpdate.setEmployments(updatedCompany.getEmployments());
        return companyMapper.toDto(companyRepository.save(companyToUpdate));
    }

    @Override
    public List<CompanyDto> getFilteredCompanies(CompanySearchCriteria companySearchCriteria) {
        return companyCriteriaRepository.findAllWithFilters(companySearchCriteria);
    }
}
