package com.emontazysta.service;

import com.emontazysta.model.dto.EmploymentDto;

import java.util.Date;
import java.util.List;

public interface EmploymentService {

    List<EmploymentDto> getAll();
    EmploymentDto getById(Long id);
    EmploymentDto add(EmploymentDto employment);
    void delete(Long id);
    EmploymentDto update(Long id, EmploymentDto employment);
    EmploymentDto getCurrentEmploymentByEmployeeId(Long employeeId);
}
