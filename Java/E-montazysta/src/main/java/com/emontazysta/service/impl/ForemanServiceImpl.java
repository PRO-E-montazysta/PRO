package com.emontazysta.service.impl;

import com.emontazysta.enums.Role;
import com.emontazysta.mapper.EmploymentMapper;
import com.emontazysta.mapper.ForemanMapper;
import com.emontazysta.model.Foreman;
import com.emontazysta.model.dto.EmployeeDto;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.model.dto.ForemanDto;
import com.emontazysta.model.searchcriteria.AppUserSearchCriteria;
import com.emontazysta.repository.ForemanRepository;
import com.emontazysta.repository.criteria.AppUserCriteriaRepository;
import com.emontazysta.repository.EmploymentRepository;
import com.emontazysta.service.ForemanService;
import com.emontazysta.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ForemanServiceImpl implements ForemanService {

    private final ForemanRepository repository;
    private final ForemanMapper foremanMapper;
    private final EmploymentRepository employmentRepository;
    private final EmploymentMapper employmentMapper;
    private final AuthUtils authUtils;
    private final AppUserCriteriaRepository appUserCriteriaRepository;

    @Override
    public List<ForemanDto> getAll(Principal principal) {
        List<EmployeeDto> appUsers = appUserCriteriaRepository.findAllWithFilters(new AppUserSearchCriteria(), principal);
        List<ForemanDto> result = new ArrayList<>();

        for(EmployeeDto employeeDto : appUsers) {
            if(employeeDto.getRoles().contains(Role.FOREMAN)) {
                result.add(foremanMapper.toDto(repository.getReferenceById(employeeDto.getId())));
            }
        }

        return result;
    }

    @Override
    public ForemanDto getById(Long id) {
        Foreman foreman = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        ForemanDto result = foremanMapper.toDto(foreman);

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
    public ForemanDto add(ForemanDto foremanDto) {
        foremanDto.setUsername(foremanDto.getUsername().toLowerCase());
        foremanDto.setRoles(Set.of(Role.FOREMAN));
        foremanDto.setUnavailabilities(new ArrayList<>());
        foremanDto.setNotifications(new ArrayList<>());
        foremanDto.setEmployeeComments(new ArrayList<>());
        foremanDto.setElementEvents(new ArrayList<>());
        foremanDto.setEmployments(new ArrayList<>());
        foremanDto.setAttachments(new ArrayList<>());
        foremanDto.setToolEvents(new ArrayList<>());
        foremanDto.setWorkingOn(new ArrayList<>());
        foremanDto.setOrdersStagesList(new ArrayList<>());
        foremanDto.setReceivedTools(new ArrayList<>());
        foremanDto.setAssignedOrders(new ArrayList<>());
        foremanDto.setElementReturnReleases(new ArrayList<>());
        foremanDto.setDemandsAdHocs(new ArrayList<>());

        Foreman foreman = repository.save(foremanMapper.toEntity(foremanDto));

        EmploymentDto employmentDto = EmploymentDto.builder()
                .dateOfEmployment(LocalDateTime.now())
                .dateOfDismiss(null)
                .companyId(authUtils.getLoggedUserCompanyId())
                .employeeId(foreman.getId())
                .build();
        employmentRepository.save(employmentMapper.toEntity(employmentDto));

        return foremanMapper.toDto(foreman);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ForemanDto update(Long id, ForemanDto foremanDto) {
        Foreman updatedForeman = foremanMapper.toEntity(foremanDto);
        Foreman foreman = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        foreman.setFirstName(updatedForeman.getFirstName());
        foreman.setLastName(updatedForeman.getLastName());
        foreman.setEmail(updatedForeman.getEmail());
        foreman.setPhone(updatedForeman.getPhone());
        foreman.setPesel(updatedForeman.getPesel());
        foreman.setUnavailabilities(updatedForeman.getUnavailabilities());
        foreman.setNotifications(updatedForeman.getNotifications());
        foreman.setEmployeeComments(updatedForeman.getEmployeeComments());
        foreman.setElementEvents(updatedForeman.getElementEvents());
        foreman.setEmployments(updatedForeman.getEmployments());
        foreman.setAttachments(updatedForeman.getAttachments());
        foreman.setToolEvents(updatedForeman.getToolEvents());
        foreman.setWorkingOn(updatedForeman.getWorkingOn());
        foreman.setReceivedTools(updatedForeman.getReceivedTools());
        foreman.setAssignedOrders(updatedForeman.getAssignedOrders());
        foreman.setElementReturnReleases(updatedForeman.getElementReturnReleases());
        foreman.setDemandsAdHocs(updatedForeman.getDemandsAdHocs());
        return foremanMapper.toDto(repository.save(foreman));
    }
}
