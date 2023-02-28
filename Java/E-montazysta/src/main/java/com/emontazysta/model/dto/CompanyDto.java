package com.emontazysta.model.dto;

import com.emontazysta.enums.CompanyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private Long id;
    private String companyName;
    private LocalDate createdAt;
    private CompanyStatus status;
    private String statusReason;
    private List<Long> warehouses;
    private List<Long> orders;
    private List<Long> clients;
    private List<Long> employments;
}
