package com.emontazysta.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EmploymentDto {

    private Long id;
    private Date dateOfEmployment;
    private Date dateOfDismiss;
}
