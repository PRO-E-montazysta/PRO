package com.emontazysta.service.impl;

import com.emontazysta.mapper.EmploymentMapper;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.Employment;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.repository.EmploymentRepository;
import com.emontazysta.service.AppUserService;
import com.emontazysta.service.EmploymentService;
import com.emontazysta.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmploymentServiceImpl implements EmploymentService {

    private final EmploymentRepository repository;
    private final EmploymentMapper employmentMapper;
    private final AppUserService appUserService;

    @Override
    public List<EmploymentDto> getAll() {
        return repository.findAll().stream()
                .map(employmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmploymentDto getById(Long id) {
        Employment employment = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return employmentMapper.toDto(employment);
    }

    @Override
    public EmploymentDto add(EmploymentDto employmentDto) {
        AppUser employee = appUserService.getById(employmentDto.getEmployeeId());

        Employment lastEmployment = employee.getEmployments().get(0);

        //Check if employee is from company
        Long loggedUserCompanyId = getCurrentEmploymentByEmployeeId(getLoggedUser().getId()).get().getCompanyId();
        if(!lastEmployment.getCompany().getId().equals(loggedUserCompanyId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        //Check if employee is still working
        if(lastEmployment.getDateOfDismiss() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        employmentDto.setCompanyId(loggedUserCompanyId);
        Employment employment = repository.save(employmentMapper.toEntity(employmentDto));
        return employmentMapper.toDto(employment);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public EmploymentDto update(Long id, EmploymentDto employmentDto) {
        Employment updatedEmployment = employmentMapper.toEntity(employmentDto);
        Employment employment = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        employment.setDateOfEmployment(updatedEmployment.getDateOfEmployment());
        employment.setDateOfDismiss(updatedEmployment.getDateOfDismiss());
        employment.setEmployee(updatedEmployment.getEmployee());

        return employmentMapper.toDto(repository.save(employment));
    }

    @Override
    public Optional<EmploymentDto> getCurrentEmploymentByEmployeeId(Long employeeId) {
        Optional<Employment> employment = Optional.ofNullable(repository.findByEmployeeIdAndDateOfDismissIsNull(employeeId));
        if (employment.isPresent())
            return Optional.ofNullable(employmentMapper.toDto(employment.get()));
        else
            return Optional.ofNullable(null);
    }

    public AppUser getLoggedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser appUser = appUserService.findByUsername(username);
        return appUser;
    }
}
