package com.emontazysta.service.impl;

import com.emontazysta.enums.Role;
import com.emontazysta.mapper.EmploymentMapper;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.Employment;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.repository.AppUserRepository;
import com.emontazysta.repository.EmploymentRepository;
import com.emontazysta.service.EmploymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmploymentServiceImpl implements EmploymentService {

    private final EmploymentRepository repository;
    private final EmploymentMapper employmentMapper;
    private final AppUserRepository appUserRepository;

    @Override
    public List<EmploymentDto> getAllEmployeeEmployments(Long id) {
        AppUser employee = appUserRepository.getById(id);


        return employee.getEmployments().stream()
                .map(employmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmploymentDto dismiss(Long employeeId) {
        Optional<EmploymentDto> dismissingCurrentEmployment = getCurrentEmploymentByEmployeeId(employeeId);

        if(dismissingCurrentEmployment.isPresent()) {
            Long loggedUserCompanyId = getLoggedUser().getEmployments().get(0).getCompany().getId();

            if(dismissingCurrentEmployment.get().getCompanyId().equals(loggedUserCompanyId)) {
                Employment employment = employmentMapper.toEntity(dismissingCurrentEmployment.get());
                employment.setDateOfDismiss(LocalDateTime.now());
                repository.save(employment);
                return null;
            }else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public EmploymentDto hire(Long employeeId) {
        //Employee for which we set employment
        AppUser employee = appUserRepository.getById(employeeId);

        //Check if user is cloud admin
        if(employee.getRoles().contains(Role.CLOUD_ADMIN)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        //Get user company
        Long employeeCompanyId = employee.getEmployments().get(0).getCompany().getId();

        //Logged user
        Long loggedUserCompanyId = getCurrentEmploymentByEmployeeId(getLoggedUser().getId()).get().getCompanyId();

        //Check if employee is from company
        if(!employeeCompanyId.equals(loggedUserCompanyId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        //Check if employee is still working
        if(getCurrentEmploymentByEmployeeId(employee.getId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        EmploymentDto employmentDto = EmploymentDto.builder()
                .dateOfEmployment(LocalDateTime.now())
                .companyId(loggedUserCompanyId)
                .employeeId(employeeId)
                .build();

        Employment employment = repository.save(employmentMapper.toEntity(employmentDto));
        return employmentMapper.toDto(employment);
    }

    @Override
    public Optional<EmploymentDto> getCurrentEmploymentByEmployeeId(Long employeeId) {
        Optional<Employment> employment = Optional.ofNullable(repository.findByEmployeeIdAndDateOfDismissIsNull(employeeId));
        if (employment.isPresent())
            return Optional.ofNullable(employmentMapper.toDto(employment.get()));
        else
            return Optional.ofNullable(null);
    }

    private AppUser getLoggedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser appUser = appUserRepository.findByUsername(username);
        return appUser;
    }
}
