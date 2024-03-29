package com.emontazysta.service.impl;

import com.emontazysta.enums.Role;
import com.emontazysta.mail.MailTemplates;
import com.emontazysta.mapper.EmploymentMapper;
import com.emontazysta.mapper.WarehouseManagerMapper;
import com.emontazysta.model.EmailData;
import com.emontazysta.model.WarehouseManager;
import com.emontazysta.model.dto.EmployeeDto;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.model.dto.WarehouseManagerDto;
import com.emontazysta.model.searchcriteria.AppUserSearchCriteria;
import com.emontazysta.repository.WarehouseManagerRepository;
import com.emontazysta.repository.criteria.AppUserCriteriaRepository;
import com.emontazysta.repository.EmploymentRepository;
import com.emontazysta.service.EmailService;
import com.emontazysta.service.WarehouseManagerService;
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
public class WarehouseManagerServiceImpl implements WarehouseManagerService {

    private final WarehouseManagerRepository repository;
    private final WarehouseManagerMapper warehouseManagerMapper;
    private final EmploymentRepository employmentRepository;
    private final EmploymentMapper employmentMapper;
    private final AuthUtils authUtils;
    private final AppUserCriteriaRepository appUserCriteriaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;

    @Override
    public List<WarehouseManagerDto> getAll(Principal principal) {
        List<EmployeeDto> appUsers = appUserCriteriaRepository.findAllWithFilters(new AppUserSearchCriteria(), principal);
        List<WarehouseManagerDto> result = new ArrayList<>();

        for(EmployeeDto employeeDto : appUsers) {
            if(employeeDto.getRoles().contains(Role.WAREHOUSE_MANAGER)) {
                result.add(warehouseManagerMapper.toDto(repository.getReferenceById(employeeDto.getId())));
            }
        }

        return result;
    }

    @Override
    public WarehouseManagerDto getById(Long id) {
        WarehouseManager warehouseManager = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        WarehouseManagerDto result = warehouseManagerMapper.toDto(warehouseManager);

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
    public WarehouseManagerDto add(WarehouseManagerDto warehouseManagerDto) {
        String password = PasswordGenerator.generatePassword(10);
        warehouseManagerDto.setUsername(warehouseManagerDto.getUsername().toLowerCase());
        warehouseManagerDto.setPassword(bCryptPasswordEncoder.encode(password));
        warehouseManagerDto.setRoles(Set.of(Role.WAREHOUSE_MANAGER));
        warehouseManagerDto.setUnavailabilities(new ArrayList<>());
        warehouseManagerDto.setNotifications(new ArrayList<>());
        warehouseManagerDto.setEmployeeComments(new ArrayList<>());
        warehouseManagerDto.setElementEvents(new ArrayList<>());
        warehouseManagerDto.setEmployments(new ArrayList<>());
        warehouseManagerDto.setAttachments(new ArrayList<>());
        warehouseManagerDto.setToolEvents(new ArrayList<>());
        warehouseManagerDto.setReleaseTools(new ArrayList<>());
        warehouseManagerDto.setElementReturnReleases(new ArrayList<>());
        warehouseManagerDto.setAcceptedDemandAdHocs(new ArrayList<>());

        WarehouseManager warehouseManager = repository.save(warehouseManagerMapper.toEntity(warehouseManagerDto));

        EmploymentDto employmentDto = EmploymentDto.builder()
                .dateOfEmployment(LocalDateTime.now())
                .dateOfDismiss(null)
                .companyId(authUtils.getLoggedUserCompanyId())
                .employeeId(warehouseManager.getId())
                .build();
        employmentRepository.save(employmentMapper.toEntity(employmentDto));

        emailService.sendEmail(
                EmailData.builder()
                        .to(warehouseManagerDto.getEmail())
                        .message(MailTemplates.employeeCreate(warehouseManagerDto.getUsername(),
                                password, warehouseManagerDto.getFirstName(), warehouseManagerDto.getLastName()))
                        .subject("Witaj w E-Montażysta!")
                        .build()
        );

        return warehouseManagerMapper.toDto(warehouseManager);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public WarehouseManagerDto update(Long id, WarehouseManagerDto warehouseManagerDto) {
        WarehouseManager updatedWarehouseManager = warehouseManagerMapper.toEntity(warehouseManagerDto);
        WarehouseManager warehouseManager = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        warehouseManager.setFirstName(updatedWarehouseManager.getFirstName());
        warehouseManager.setLastName(updatedWarehouseManager.getLastName());
        warehouseManager.setEmail(updatedWarehouseManager.getEmail());
        warehouseManager.setPhone(updatedWarehouseManager.getPhone());
        warehouseManager.setPesel(updatedWarehouseManager.getPesel());
        warehouseManager.setUnavailabilities(updatedWarehouseManager.getUnavailabilities());
        warehouseManager.setNotifications(updatedWarehouseManager.getNotifications());
        warehouseManager.setEmployeeComments(updatedWarehouseManager.getEmployeeComments());
        warehouseManager.setElementEvents(updatedWarehouseManager.getElementEvents());
        warehouseManager.setEmployments(updatedWarehouseManager.getEmployments());
        warehouseManager.setAttachments(updatedWarehouseManager.getAttachments());
        warehouseManager.setToolEvents(updatedWarehouseManager.getToolEvents());
        warehouseManager.setReleasedTools(updatedWarehouseManager.getReleasedTools());
        warehouseManager.setElementReturnReleases(updatedWarehouseManager.getElementReturnReleases());
        warehouseManager.setAcceptedDemandAdHocs(updatedWarehouseManager.getAcceptedDemandAdHocs());
        return warehouseManagerMapper.toDto(repository.save(warehouseManager));
    }
}
