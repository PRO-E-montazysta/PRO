package com.emontazysta.service.impl;

import com.emontazysta.mapper.CompanyAdminMapper;
import com.emontazysta.model.dto.CompanyAdminDto;
import com.emontazysta.model.CompanyAdmin;
import com.emontazysta.repository.CompanyAdminRepository;
import com.emontazysta.service.CompanyAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyAdminServiceImpl implements CompanyAdminService {

    private final CompanyAdminRepository repository;
    private final CompanyAdminMapper mapper;

    @Override
    public List<CompanyAdminDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyAdminDto getById(Long id) {
        CompanyAdmin companyAdmin = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapper.toDto(companyAdmin);
    }

    @Override
    public CompanyAdminDto add(CompanyAdminDto companyAdminDto) {
        companyAdminDto.setUsername(companyAdminDto.getUsername().toLowerCase());
        CompanyAdmin companyAdmin = mapper.toEntity(companyAdminDto);
        return mapper.toDto(repository.save(companyAdmin));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public CompanyAdminDto update(Long id, CompanyAdminDto companyAdminDto) {
        CompanyAdmin updatedCompanyAdmin = mapper.toEntity(companyAdminDto);
        CompanyAdmin companyAdmin = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        companyAdmin.setFirstName(updatedCompanyAdmin.getFirstName());
        companyAdmin.setLastName(updatedCompanyAdmin.getLastName());
        companyAdmin.setEmail(updatedCompanyAdmin.getEmail());
        companyAdmin.setPhone(updatedCompanyAdmin.getPhone());
        companyAdmin.setPesel(updatedCompanyAdmin.getPesel());
        companyAdmin.setUnavailabilities(updatedCompanyAdmin.getUnavailabilities());
        companyAdmin.setNotifications(updatedCompanyAdmin.getNotifications());
        companyAdmin.setEmployeeComments(updatedCompanyAdmin.getEmployeeComments());
        companyAdmin.setElementEvents(updatedCompanyAdmin.getElementEvents());
        companyAdmin.setEmployments(updatedCompanyAdmin.getEmployments());
        companyAdmin.setAttachments(updatedCompanyAdmin.getAttachments());
        companyAdmin.setToolEvents(updatedCompanyAdmin.getToolEvents());

        return mapper.toDto(repository.save(companyAdmin));
    }
}
