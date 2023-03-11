package com.emontazysta.service.impl;

import com.emontazysta.mapper.ManagerMapper;
import com.emontazysta.model.Manager;
import com.emontazysta.model.dto.ManagerDto;
import com.emontazysta.repository.ManagerRepository;
import com.emontazysta.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository repository;
    private final ManagerMapper managerMapper;

    @Override
    public List<ManagerDto> getAll() {
        return repository.findAll().stream()
                .map(managerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ManagerDto getById(Long id) {
        Manager manager = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return managerMapper.toDto(manager);
    }

    @Override
    public ManagerDto add(ManagerDto managerDto) {
        Manager manager = managerMapper.toEntity(managerDto);
        return managerMapper.toDto(repository.save(manager));
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
        manager.setUsername(updatedManager.getUsername());
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
