package com.emontazysta.model.dto;

import com.emontazysta.enums.CompanyStatus;
import com.emontazysta.model.Client;
import com.emontazysta.model.Warehouse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class CompanyDto {

    private Long id;
    private String companyName;
    private Date createdAt;
    private CompanyStatus status;
    private String statusReason;
    private List<Long> warehouses;
    private List<Long> orders;
    private List<Long> clients;
    private List<Long> employments;
}
