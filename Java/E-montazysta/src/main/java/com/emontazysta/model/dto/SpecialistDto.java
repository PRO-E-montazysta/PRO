package com.emontazysta.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class SpecialistDto extends EmployeeDto {

    private List<Long> orders;
    private List<Long> demandAdHocs;
}
