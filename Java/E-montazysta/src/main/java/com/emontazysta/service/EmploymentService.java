package com.emontazysta.service;

import com.emontazysta.model.dto.EmploymentDto;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EmploymentService {

    List<EmploymentDto> getAllEmployeeEmployments(Long employeeId);
    EmploymentDto hire(Long employeeId);
    EmploymentDto dismiss(Long employeeId);
    Optional<EmploymentDto> getCurrentEmploymentByEmployeeId(Long employeeId);
}
