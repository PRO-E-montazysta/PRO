package com.emontazysta.mapper;

import com.emontazysta.model.Employment;
import com.emontazysta.model.dto.EmploymentDto;

public class EmploymentMapper {

    public static EmploymentDto employmentToDto (Employment employment) {
        return EmploymentDto.builder()
                .id(employment.getId())
                .dateOfEmployment(employment.getDateOfEmployment())
                .dateOfDismiss(employment.getDateOfDismiss())
                .build();
    }
}
