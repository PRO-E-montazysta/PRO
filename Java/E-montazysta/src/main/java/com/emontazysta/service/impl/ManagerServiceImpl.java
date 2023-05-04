package com.emontazysta.service.impl;

import com.emontazysta.enums.Role;
import com.emontazysta.mapper.ManagerMapper;
import com.emontazysta.model.Manager;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.model.dto.ManagerDto;
import com.emontazysta.repository.ManagerRepository;
import com.emontazysta.service.EmploymentService;
import com.emontazysta.service.ManagerService;
import com.emontazysta.util.AuthUtils;
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
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository repository;
    private final ManagerMapper managerMapper;
    private final EmploymentService employmentService;
    private final AuthUtils authUtils;

    @Override
    public List<ManagerDto> getAll() {
        return repository.findAll().stream()
                .map(managerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ManagerDto getById(Long id) {
        Manager manager = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        ManagerDto result = managerMapper.toDto(manager);

        if(!authUtils.getLoggedUser().getRoles().contains(Role.ADMIN)) {
            result.setUsername(null);
        }
        if(!authUtils.getLoggedUser().getRoles().contains(Role.ADMIN) ||
                !authUtils.getLoggedUser().getRoles().contains(Role.MANAGER)) {
            result.setPesel(null);
        }

        return result;
    }

    @Override
    public ManagerDto add(ManagerDto managerDto) {
        managerDto.setUsername(managerDto.getUsername().toLowerCase());
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
        managerDto.setDemandsAdHocs(new ArrayList<>());
        managerDto.setElementEvents(new ArrayList<>());

        Manager manager = repository.save(managerMapper.toEntity(managerDto));

        EmploymentDto employmentDto = EmploymentDto.builder()
                .dateOfEmployment(LocalDateTime.now())
                .dateOfDismiss(null)
                .companyId(authUtils.getLoggedUserCompanyId())
                .employeeId(manager.getId())
                .build();
        employmentService.add(employmentDto);

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
        manager.setDemandsAdHocs(updatedManager.getDemandsAdHocs());
        manager.setElementEvents(updatedManager.getElementEvents());
        return managerMapper.toDto(repository.save(manager));
    }
}
