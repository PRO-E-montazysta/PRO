package com.emontazysta.service.impl;

import com.emontazysta.mail.MailTemplates;
import com.emontazysta.mapper.CompanyAdminMapper;
import com.emontazysta.mapper.CompanyWithAdminMapper;
import com.emontazysta.mapper.EmploymentMapper;
import com.emontazysta.model.EmailData;
import com.emontazysta.model.Employment;
import com.emontazysta.model.dto.CompanyAdminDto;
import com.emontazysta.model.CompanyAdmin;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.repository.CompanyAdminRepository;
import com.emontazysta.repository.EmploymentRepository;
import com.emontazysta.service.CompanyAdminService;
import com.emontazysta.service.CompanyService;
import com.emontazysta.service.EmailService;
import com.emontazysta.service.EmploymentService;
import com.emontazysta.util.AuthUtils;
import com.emontazysta.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyAdminServiceImpl implements CompanyAdminService {

    private final CompanyAdminRepository repository;
    private final CompanyAdminMapper mapper;
    private final EmailService emailService;
    private final EmploymentService employmentService;
    private final CompanyService companyService;
    private final AuthUtils authUtils;

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
        companyAdminDto.setPassword(PasswordGenerator.generatePassword(10));
        CompanyAdmin companyAdmin =  repository.save(mapper.toEntity(companyAdminDto));

        employmentService.add(
                EmploymentDto.builder()
                        .dateOfEmployment(LocalDateTime.now())
                        .companyId(authUtils.getLoggedUserCompanyId())
                        .employeeId(companyAdmin.getId())
                        .build()
        );

        emailService.sendEmail(
                EmailData.builder()
                        .to(companyAdminDto.getEmail())
                        .message(MailTemplates.employeeCreate(companyAdminDto.getUsername(),
                                companyAdminDto.getPassword(), companyAdminDto.getFirstName(), companyAdminDto.getLastName()))
                        .subject("Witaj w E-Montażysta!")
                        .build()
        );

        return mapper.toDto(companyAdmin);
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
