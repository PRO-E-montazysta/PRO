package com.emontazysta.service.impl;

import com.emontazysta.mail.MailTemplates;
import com.emontazysta.mapper.CompanyAdminMapper;
import com.emontazysta.mapper.EmploymentMapper;
import com.emontazysta.model.EmailData;
import com.emontazysta.enums.Role;
import com.emontazysta.model.dto.CompanyAdminDto;
import com.emontazysta.model.CompanyAdmin;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.repository.CompanyAdminRepository;
import com.emontazysta.repository.EmploymentRepository;
import com.emontazysta.service.CompanyAdminService;
import com.emontazysta.service.CompanyService;
import com.emontazysta.service.EmailService;
import com.emontazysta.util.AuthUtils;
import com.emontazysta.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyAdminServiceImpl implements CompanyAdminService {

    private final CompanyAdminRepository repository;
    private final CompanyAdminMapper mapper;
    private final EmailService emailService;
    private final EmploymentRepository employmentRepository;
    private final EmploymentMapper employmentMapper;
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
        CompanyAdminDto result = mapper.toDto(companyAdmin);

        if(!authUtils.getLoggedUser().getRoles().contains(Role.ADMIN)) {
            result.setUsername(null);
        }
        if(!(authUtils.getLoggedUser().getRoles().contains(Role.ADMIN) ||
                authUtils.getLoggedUser().getRoles().contains(Role.MANAGER))) {
            result.setPesel(null);
        }

        return result;
    }

    @Override
    public CompanyAdminDto add(CompanyAdminDto companyAdminDto) {
        companyAdminDto.setUsername(companyAdminDto.getUsername().toLowerCase());
        //companyAdminDto.setPassword(PasswordGenerator.generatePassword(10));  //TODO: uncomment to generate password
        companyAdminDto.setRoles(Set.of(Role.ADMIN));
        companyAdminDto.setUnavailabilities(new ArrayList<>());
        companyAdminDto.setNotifications(new ArrayList<>());
        companyAdminDto.setEmployeeComments(new ArrayList<>());
        companyAdminDto.setElementEvents(new ArrayList<>());
        companyAdminDto.setEmployments(new ArrayList<>());
        companyAdminDto.setAttachments(new ArrayList<>());
        companyAdminDto.setToolEvents(new ArrayList<>());

        CompanyAdmin companyAdmin = repository.save(mapper.toEntity(companyAdminDto));

        EmploymentDto employmentDto = EmploymentDto.builder()
                .dateOfEmployment(LocalDateTime.now())
                .dateOfDismiss(null)
                .companyId(authUtils.getLoggedUserCompanyId())
                .employeeId(companyAdmin.getId())
                .build();
        employmentRepository.save(employmentMapper.toEntity(employmentDto));


        //TODO: uncomment to send email on company admin create
        /*emailService.sendEmail(
                EmailData.builder()
                        .to(companyAdminDto.getEmail())
                        .message(MailTemplates.employeeCreate(companyAdminDto.getUsername(),
                                companyAdminDto.getPassword(), companyAdminDto.getFirstName(), companyAdminDto.getLastName()))
                        .subject("Witaj w E-Montażysta!")
                        .build()
        );*/

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
