package com.emontazysta.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class SalesRepresentativeDto extends EmployeeDto {

    private List<Long> orders;
}
