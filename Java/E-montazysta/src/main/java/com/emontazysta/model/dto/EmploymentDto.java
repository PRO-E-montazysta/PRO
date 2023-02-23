package com.emontazysta.model.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class EmploymentDto {

    private Long id;
    private LocalDate dateOfEmployment;
    private LocalDate dateOfDismiss;
}
