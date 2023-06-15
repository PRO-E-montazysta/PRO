package com.emontazysta.service.impl;

import com.emontazysta.enums.Role;
import com.emontazysta.mapper.EmploymentMapper;
import com.emontazysta.mapper.SalesRepresentativeMapper;
import com.emontazysta.model.SalesRepresentative;
import com.emontazysta.model.dto.EmployeeDto;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.model.dto.SalesRepresentativeDto;
import com.emontazysta.model.searchcriteria.AppUserSearchCriteria;
import com.emontazysta.repository.SalesRepresentativeRepository;
import com.emontazysta.repository.criteria.AppUserCriteriaRepository;
import com.emontazysta.repository.EmploymentRepository;
import com.emontazysta.service.SalesRepresentativeService;
import com.emontazysta.util.AuthUtils;
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
public class SalesRepresentativeServiceImpl implements SalesRepresentativeService {

    private final SalesRepresentativeRepository repository;
    private final SalesRepresentativeMapper salesRepresentativeMapper;
    private final EmploymentRepository employmentRepository;
    private final EmploymentMapper employmentMapper;
    private final AuthUtils authUtils;
    private final AppUserCriteriaRepository appUserCriteriaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<SalesRepresentativeDto> getAll(Principal principal) {
        List<EmployeeDto> appUsers = appUserCriteriaRepository.findAllWithFilters(new AppUserSearchCriteria(), principal);
        List<SalesRepresentativeDto> result = new ArrayList<>();

        for(EmployeeDto employeeDto : appUsers) {
            if(employeeDto.getRoles().contains(Role.SALES_REPRESENTATIVE)) {
                result.add(salesRepresentativeMapper.toDto(repository.getReferenceById(employeeDto.getId())));
            }
        }

        return result;
    }

    @Override
    public SalesRepresentativeDto getById(Long id) {
        SalesRepresentative salesRepresentative = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        SalesRepresentativeDto result = salesRepresentativeMapper.toDto(salesRepresentative);

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
    public SalesRepresentativeDto add(SalesRepresentativeDto salesRepresentativeDto) {
        salesRepresentativeDto.setUsername(salesRepresentativeDto.getUsername().toLowerCase());
        salesRepresentativeDto.setPassword(bCryptPasswordEncoder.encode(salesRepresentativeDto.getPassword()));
        salesRepresentativeDto.setRoles(Set.of(Role.SALES_REPRESENTATIVE));
        salesRepresentativeDto.setUnavailabilities(new ArrayList<>());
        salesRepresentativeDto.setNotifications(new ArrayList<>());
        salesRepresentativeDto.setEmployeeComments(new ArrayList<>());
        salesRepresentativeDto.setElementEvents(new ArrayList<>());
        salesRepresentativeDto.setEmployments(new ArrayList<>());
        salesRepresentativeDto.setAttachments(new ArrayList<>());
        salesRepresentativeDto.setToolEvents(new ArrayList<>());
        salesRepresentativeDto.setOrders(new ArrayList<>());

        SalesRepresentative salesRepresentative = repository.save(salesRepresentativeMapper.toEntity(salesRepresentativeDto));

        EmploymentDto employmentDto = EmploymentDto.builder()
                .dateOfEmployment(LocalDateTime.now())
                .dateOfDismiss(null)
                .companyId(authUtils.getLoggedUserCompanyId())
                .employeeId(salesRepresentative.getId())
                .build();
        employmentRepository.save(employmentMapper.toEntity(employmentDto));

        return salesRepresentativeMapper.toDto(salesRepresentative);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public SalesRepresentativeDto update(Long id, SalesRepresentativeDto salesRepresentativeDto) {
        SalesRepresentative updatedSalesRepresentative = salesRepresentativeMapper.toEntity(salesRepresentativeDto);
        SalesRepresentative salesRepresentative = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        salesRepresentative.setFirstName(updatedSalesRepresentative.getFirstName());
        salesRepresentative.setLastName(updatedSalesRepresentative.getLastName());
        salesRepresentative.setEmail(updatedSalesRepresentative.getEmail());
        salesRepresentative.setPhone(updatedSalesRepresentative.getPhone());
        salesRepresentative.setPesel(updatedSalesRepresentative.getPesel());
        salesRepresentative.setUnavailabilities(updatedSalesRepresentative.getUnavailabilities());
        salesRepresentative.setNotifications(updatedSalesRepresentative.getNotifications());
        salesRepresentative.setEmployeeComments(updatedSalesRepresentative.getEmployeeComments());
        salesRepresentative.setElementEvents(updatedSalesRepresentative.getElementEvents());
        salesRepresentative.setEmployments(updatedSalesRepresentative.getEmployments());
        salesRepresentative.setAttachments(updatedSalesRepresentative.getAttachments());
        salesRepresentative.setToolEvents(updatedSalesRepresentative.getToolEvents());
        salesRepresentative.setOrders(updatedSalesRepresentative.getOrders());
        return salesRepresentativeMapper.toDto(repository.save(salesRepresentative));
    }
}
