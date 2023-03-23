package com.emontazysta.service.impl;

import com.emontazysta.mapper.EmploymentMapper;
import com.emontazysta.model.Employment;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.repository.EmploymentRepository;
import com.emontazysta.service.EmploymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmploymentServiceImpl implements EmploymentService {

    private final EmploymentRepository repository;
    private final EmploymentMapper employmentMapper;

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
        employment.setCompany(updatedEmployment.getCompany());
        employment.setEmployee(updatedEmployment.getEmployee());
        return employmentMapper.toDto(repository.save(employment));
    }

    @Override
    public EmploymentDto getCurrentEmploymentByEmployeeId(Long employeeId) {
        return employmentMapper.toDto(repository.findByEmployeeIdAndDateOfDismissIsNull(employeeId));
    }
}
