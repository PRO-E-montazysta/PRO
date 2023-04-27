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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmploymentServiceImpl implements EmploymentService {

    private final EmploymentRepository repository;
    private final EmploymentMapper employmentMapper;

    @Override
    public List<EmploymentDto> getAll() {
        return repository.findAllByDeletedIsFalse().stream()
                .map(employmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmploymentDto getById(Long id) {
        Employment employment = repository.findByIdAndDeletedIsFalse(id).orElseThrow(EntityNotFoundException::new);
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
        Employment employment = repository.findByIdAndDeletedIsFalse(id).orElseThrow(EntityNotFoundException::new);
        employment.setDateOfEmployment(updatedEmployment.getDateOfEmployment());
        employment.setDateOfDismiss(updatedEmployment.getDateOfDismiss());
        employment.setCompany(updatedEmployment.getCompany());
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
}
