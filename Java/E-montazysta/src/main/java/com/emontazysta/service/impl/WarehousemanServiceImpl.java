package com.emontazysta.service.impl;

import com.emontazysta.enums.Role;
import com.emontazysta.mail.MailTemplates;
import com.emontazysta.mapper.EmploymentMapper;
import com.emontazysta.mapper.WarehousemanMapper;
import com.emontazysta.model.EmailData;
import com.emontazysta.model.Warehouseman;
import com.emontazysta.model.dto.EmployeeDto;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.model.dto.WarehousemanDto;
import com.emontazysta.model.searchcriteria.AppUserSearchCriteria;
import com.emontazysta.repository.WarehousemanRepository;
import com.emontazysta.repository.criteria.AppUserCriteriaRepository;
import com.emontazysta.repository.EmploymentRepository;
import com.emontazysta.service.EmailService;
import com.emontazysta.service.WarehousemanService;
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
public class WarehousemanServiceImpl implements WarehousemanService {

    private final WarehousemanRepository repository;
    private final WarehousemanMapper warehousemanMapper;
    private final EmploymentRepository employmentRepository;
    private final EmploymentMapper employmentMapper;
    private final AuthUtils authUtils;
    private final AppUserCriteriaRepository appUserCriteriaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;

    @Override
    public List<WarehousemanDto> getAll(Principal principal) {
        List<EmployeeDto> appUsers = appUserCriteriaRepository.findAllWithFilters(new AppUserSearchCriteria(), principal);
        List<WarehousemanDto> result = new ArrayList<>();

        for(EmployeeDto employeeDto : appUsers) {
            if(employeeDto.getRoles().contains(Role.WAREHOUSE_MAN)) {
                result.add(warehousemanMapper.toDto(repository.getReferenceById(employeeDto.getId())));
            }
        }

        return result;
    }

    @Override
    public WarehousemanDto getById(Long id) {
        Warehouseman warehouseman = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        WarehousemanDto result = warehousemanMapper.toDto(warehouseman);

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
    public WarehousemanDto add(WarehousemanDto warehousemanDto) {
        String password = PasswordGenerator.generatePassword(10);
        warehousemanDto.setUsername(warehousemanDto.getUsername().toLowerCase());
        warehousemanDto.setPassword(bCryptPasswordEncoder.encode(password));
        warehousemanDto.setRoles(Set.of(Role.WAREHOUSE_MAN));
        warehousemanDto.setUnavailabilities(new ArrayList<>());
        warehousemanDto.setNotifications(new ArrayList<>());
        warehousemanDto.setEmployeeComments(new ArrayList<>());
        warehousemanDto.setElementEvents(new ArrayList<>());
        warehousemanDto.setEmployments(new ArrayList<>());
        warehousemanDto.setAttachments(new ArrayList<>());
        warehousemanDto.setToolEvents(new ArrayList<>());
        warehousemanDto.setReleaseTools(new ArrayList<>());
        warehousemanDto.setElementReturnReleases(new ArrayList<>());

        Warehouseman warehouseman = repository.save(warehousemanMapper.toEntity(warehousemanDto));

        EmploymentDto employmentDto = EmploymentDto.builder()
                .dateOfEmployment(LocalDateTime.now())
                .dateOfDismiss(null)
                .companyId(authUtils.getLoggedUserCompanyId())
                .employeeId(warehouseman.getId())
                .build();
        employmentRepository.save(employmentMapper.toEntity(employmentDto));

        emailService.sendEmail(
                EmailData.builder()
                        .to(warehousemanDto.getEmail())
                        .message(MailTemplates.employeeCreate(warehousemanDto.getUsername(),
                                password, warehousemanDto.getFirstName(), warehousemanDto.getLastName()))
                        .subject("Witaj w E-Montażysta!")
                        .build()
        );

        return warehousemanMapper.toDto(warehouseman);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public WarehousemanDto update(Long id, WarehousemanDto warehousemanDto) {
        Warehouseman updatedWarehouseman = warehousemanMapper.toEntity(warehousemanDto);
        Warehouseman warehouseman = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        warehouseman.setFirstName(updatedWarehouseman.getFirstName());
        warehouseman.setLastName(updatedWarehouseman.getLastName());
        warehouseman.setEmail(updatedWarehouseman.getEmail());
        warehouseman.setPhone(updatedWarehouseman.getPhone());
        warehouseman.setPesel(updatedWarehouseman.getPesel());
        warehouseman.setUnavailabilities(updatedWarehouseman.getUnavailabilities());
        warehouseman.setNotifications(updatedWarehouseman.getNotifications());
        warehouseman.setEmployeeComments(updatedWarehouseman.getEmployeeComments());
        warehouseman.setElementEvents(updatedWarehouseman.getElementEvents());
        warehouseman.setEmployments(updatedWarehouseman.getEmployments());
        warehouseman.setAttachments(updatedWarehouseman.getAttachments());
        warehouseman.setToolEvents(updatedWarehouseman.getToolEvents());
        warehouseman.setReleasedTools(updatedWarehouseman.getReleasedTools());
        warehouseman.setElementReturnReleases(updatedWarehouseman.getElementReturnReleases());
        return warehousemanMapper.toDto(repository.save(warehouseman));
    }
}
