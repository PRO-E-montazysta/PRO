package com.emontazysta.service.impl;

import com.emontazysta.enums.Role;
import com.emontazysta.mapper.WarehousemanMapper;
import com.emontazysta.model.Warehouseman;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.model.dto.WarehousemanDto;
import com.emontazysta.repository.WarehousemanRepository;
import com.emontazysta.service.EmploymentService;
import com.emontazysta.service.WarehousemanService;
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
public class WarehousemanServiceImpl implements WarehousemanService {

    private final WarehousemanRepository repository;
    private final WarehousemanMapper warehousemanMapper;
    private final EmploymentService employmentService;
    private final AuthUtils authUtils;

    @Override
    public List<WarehousemanDto> getAll() {
        return repository.findAllByDeletedIsFalse().stream()
                .map(warehousemanMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public WarehousemanDto getById(Long id) {
        Warehouseman warehouseman = repository.findByIdAndDeletedIsFalse(id).orElseThrow(EntityNotFoundException::new);
        WarehousemanDto result = warehousemanMapper.toDto(warehouseman);

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
    public WarehousemanDto add(WarehousemanDto warehousemanDto) {
        warehousemanDto.setUsername(warehousemanDto.getUsername().toLowerCase());
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
        employmentService.add(employmentDto);

        return warehousemanMapper.toDto(warehouseman);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public WarehousemanDto update(Long id, WarehousemanDto warehousemanDto) {
        Warehouseman updatedWarehouseman = warehousemanMapper.toEntity(warehousemanDto);
        Warehouseman warehouseman = repository.findByIdAndDeletedIsFalse(id).orElseThrow(EntityNotFoundException::new);
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
