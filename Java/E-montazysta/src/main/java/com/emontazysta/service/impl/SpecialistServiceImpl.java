package com.emontazysta.service.impl;

import com.emontazysta.enums.Role;
import com.emontazysta.mapper.EmploymentMapper;
import com.emontazysta.mapper.SpecialistMapper;
import com.emontazysta.model.Specialist;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.model.dto.SpecialistDto;
import com.emontazysta.repository.EmploymentRepository;
import com.emontazysta.repository.SpecialistRepository;
import com.emontazysta.service.SpecialistService;
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
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository repository;
    private final SpecialistMapper specialistMapper;
    private final EmploymentRepository employmentRepository;
    private final EmploymentMapper employmentMapper;
    private final AuthUtils authUtils;

    @Override
    public List<SpecialistDto> getAll() {
        return repository.findAll().stream()
                .map(specialistMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SpecialistDto getById(Long id) {
        Specialist specialist = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        SpecialistDto result = specialistMapper.toDto(specialist);

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
    public SpecialistDto add(SpecialistDto specialistDto) {
        specialistDto.setUsername(specialistDto.getUsername().toLowerCase());
        specialistDto.setRoles(Set.of(Role.SPECIALIST));
        specialistDto.setUnavailabilities(new ArrayList<>());
        specialistDto.setNotifications(new ArrayList<>());
        specialistDto.setEmployeeComments(new ArrayList<>());
        specialistDto.setElementEvents(new ArrayList<>());
        specialistDto.setEmployments(new ArrayList<>());
        specialistDto.setAttachments(new ArrayList<>());
        specialistDto.setToolEvents(new ArrayList<>());
        specialistDto.setOrders(new ArrayList<>());
        specialistDto.setDemandAdHocs(new ArrayList<>());

        Specialist specialist = repository.save(specialistMapper.toEntity(specialistDto));

        EmploymentDto employmentDto = EmploymentDto.builder()
                .dateOfEmployment(LocalDateTime.now())
                .dateOfDismiss(null)
                .companyId(authUtils.getLoggedUserCompanyId())
                .employeeId(specialist.getId())
                .build();
        employmentRepository.save(employmentMapper.toEntity(employmentDto));

        return specialistMapper.toDto(specialist);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public SpecialistDto update(Long id, SpecialistDto specialistDto) {
        Specialist updatedSpecialist = specialistMapper.toEntity(specialistDto);
        Specialist specialist = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        specialist.setFirstName(updatedSpecialist.getFirstName());
        specialist.setLastName(updatedSpecialist.getLastName());
        specialist.setEmail(updatedSpecialist.getEmail());
        specialist.setPhone(updatedSpecialist.getPhone());
        specialist.setPesel(updatedSpecialist.getPesel());
        specialist.setUnavailabilities(updatedSpecialist.getUnavailabilities());
        specialist.setNotifications(updatedSpecialist.getNotifications());
        specialist.setEmployeeComments(updatedSpecialist.getEmployeeComments());
        specialist.setElementEvents(updatedSpecialist.getElementEvents());
        specialist.setEmployments(updatedSpecialist.getEmployments());
        specialist.setAttachments(updatedSpecialist.getAttachments());
        specialist.setToolEvents(updatedSpecialist.getToolEvents());
        specialist.setOrders(updatedSpecialist.getOrders());
        specialist.setDemandAdHocs(updatedSpecialist.getDemandAdHocs());
        specialist.setOrders(updatedSpecialist.getOrders());
        specialist.setDemandAdHocs(updatedSpecialist.getDemandAdHocs());
        return specialistMapper.toDto(repository.save(specialist));
    }
}
