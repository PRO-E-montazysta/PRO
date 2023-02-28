package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfPriority;
import com.emontazysta.enums.TypeOfStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
public class OrdersCompanyManagerDto {
    private Long id;
    private String name;
    private TypeOfStatus typeOfStatus;
    private LocalDateTime plannedStart;
    private LocalDateTime plannedEnd;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private TypeOfPriority typeOfPriority;
    private Long companyId;
    private String companyName;
    private Long managerId;
    private String managerFirstName;
    private String managerLastName;
    private Long foremanId;
    private Long specialistId;
    private Long salesRepresentativeId;
    private Long locationId;
    private Long clientId;
    private List<Long> orderStages;
    private List<Long> attachments;
}