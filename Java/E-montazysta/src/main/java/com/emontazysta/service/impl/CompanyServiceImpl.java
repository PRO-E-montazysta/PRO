package com.emontazysta.service.impl;

import com.emontazysta.mapper.CompanyMapper;
import com.emontazysta.model.Company;
import com.emontazysta.model.dto.CompanyDto;
import com.emontazysta.repository.CompanyRepository;
import com.emontazysta.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository repository;
    private final CompanyMapper companyMapper;

    @Override
    public List<CompanyDto> getAll() {
        return repository.findAll().stream()
                .map(companyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDto getById(Long id) {
        Company company = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return companyMapper.toDto(company);
    }

    @Override
    public CompanyDto add(CompanyDto companyDto) {
        Company company = companyMapper.toEntity(companyDto);
        company.setCreatedAt(new Date());

        return companyMapper.toDto(repository.save(company));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public CompanyDto update(Long id, CompanyDto companyDto) {

        Company updatedCompany = companyMapper.toEntity(companyDto);
        Company companyToUpdate = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        companyToUpdate.setCompanyName(updatedCompany.getCompanyName());
        companyToUpdate.setStatus(updatedCompany.getStatus());
        companyToUpdate.setStatusReason(updatedCompany.getStatusReason());
        companyToUpdate.setWarehouses(updatedCompany.getWarehouses());
        companyToUpdate.setOrders(updatedCompany.getOrders());
        companyToUpdate.setClients(updatedCompany.getClients());
        companyToUpdate.setEmployments(updatedCompany.getEmployments());
        return companyMapper.toDto(repository.save(companyToUpdate));
    }
}
