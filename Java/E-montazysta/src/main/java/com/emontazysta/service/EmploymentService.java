package com.emontazysta.service;

import com.emontazysta.model.dto.EmploymentDto;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EmploymentService {

    List<EmploymentDto> getAll();
    List<EmploymentDto> getAllEmployeeEmployments(Long id);
    EmploymentDto getById(Long id);
    EmploymentDto add(EmploymentDto employment);
    void delete(Long id);
    EmploymentDto update(Long id, EmploymentDto employment);
    Optional<EmploymentDto> getCurrentEmploymentByEmployeeId(Long employeeId);
}
