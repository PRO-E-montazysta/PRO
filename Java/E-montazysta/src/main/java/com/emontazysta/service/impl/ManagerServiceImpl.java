package com.emontazysta.service.impl;

import com.emontazysta.enums.Role;
import com.emontazysta.mail.MailTemplates;
import com.emontazysta.mapper.EmploymentMapper;
import com.emontazysta.mapper.ManagerMapper;
import com.emontazysta.model.EmailData;
import com.emontazysta.model.Manager;
import com.emontazysta.model.dto.EmployeeDto;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.model.dto.ManagerDto;
import com.emontazysta.model.searchcriteria.AppUserSearchCriteria;
import com.emontazysta.repository.ManagerRepository;
import com.emontazysta.repository.criteria.AppUserCriteriaRepository;
import com.emontazysta.repository.EmploymentRepository;
import com.emontazysta.service.EmailService;
import com.emontazysta.service.ManagerService;
import com.emontazysta.util.AuthUtils;
import com.emontazysta.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository repository;
    private final ManagerMapper managerMapper;
    private final EmploymentRepository employmentRepository;
    private final EmploymentMapper employmentMapper;
    private final AuthUtils authUtils;
    private final AppUserCriteriaRepository appUserCriteriaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;

    @Override
    public List<ManagerDto> getAll(Principal principal) {
        List<EmployeeDto> appUsers = appUserCriteriaRepository.findAllWithFilters(new AppUserSearchCriteria(), principal);
        List<ManagerDto> result = new ArrayList<>();

        for(EmployeeDto employeeDto : appUsers) {
            if(employeeDto.getRoles().contains(Role.MANAGER)) {
                result.add(managerMapper.toDto(repository.getReferenceById(employeeDto.getId())));
            }
        }

        return result;
    }

    @Override
    public ManagerDto getById(Long id) {
        Manager manager = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        ManagerDto result = managerMapper.toDto(manager);

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
    public ManagerDto add(ManagerDto managerDto) {
        String password = PasswordGenerator.generatePassword(10);
        managerDto.setUsername(managerDto.getUsername().toLowerCase());
        managerDto.setPassword(bCryptPasswordEncoder.encode(password));
        managerDto.setRoles(Set.of(Role.MANAGER));
        managerDto.setUnavailabilities(new ArrayList<>());
        managerDto.setNotifications(new ArrayList<>());
        managerDto.setEmployeeComments(new ArrayList<>());
        managerDto.setElementEvents(new ArrayList<>());
        managerDto.setEmployments(new ArrayList<>());
        managerDto.setAttachments(new ArrayList<>());
        managerDto.setToolEvents(new ArrayList<>());
        managerDto.setCreatedUnavailabilities(new ArrayList<>());
        managerDto.setAcceptedEvents(new ArrayList<>());
        managerDto.setManagedOrders(new ArrayList<>());
        managerDto.setElementEvents(new ArrayList<>());

        Manager manager = repository.save(managerMapper.toEntity(managerDto));

        EmploymentDto employmentDto = EmploymentDto.builder()
                .dateOfEmployment(LocalDateTime.now())
                .dateOfDismiss(null)
                .companyId(authUtils.getLoggedUserCompanyId())
                .employeeId(manager.getId())
                .build();
        employmentRepository.save(employmentMapper.toEntity(employmentDto));

        emailService.sendEmail(
                EmailData.builder()
                        .to(managerDto.getEmail())
                        .message(MailTemplates.employeeCreate(managerDto.getUsername(),
                                password, managerDto.getFirstName(), managerDto.getLastName()))
                        .subject("Witaj w E-Montażysta!")
                        .build()
        );

        return managerMapper.toDto(manager);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ManagerDto update(Long id, ManagerDto managerDto) {
        Manager updatedManager = managerMapper.toEntity(managerDto);
        Manager manager = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        manager.setFirstName(updatedManager.getFirstName());
        manager.setLastName(updatedManager.getLastName());
        manager.setEmail(updatedManager.getEmail());
        manager.setPhone(updatedManager.getPhone());
        manager.setPesel(updatedManager.getPesel());
        manager.setUnavailabilities(updatedManager.getUnavailabilities());
        manager.setNotifications(updatedManager.getNotifications());
        manager.setEmployeeComments(updatedManager.getEmployeeComments());
        manager.setElementEvents(updatedManager.getElementEvents());
        manager.setEmployments(updatedManager.getEmployments());
        manager.setAttachments(updatedManager.getAttachments());
        manager.setToolEvents(updatedManager.getToolEvents());
        manager.setCreatedUnavailabilities(updatedManager.getCreatedUnavailabilities());
        manager.setAcceptedEvents(updatedManager.getAcceptedEvents());
        manager.setManagedOrders(updatedManager.getManagedOrders());
        manager.setElementEvents(updatedManager.getElementEvents());
        return managerMapper.toDto(repository.save(manager));
    }
}
