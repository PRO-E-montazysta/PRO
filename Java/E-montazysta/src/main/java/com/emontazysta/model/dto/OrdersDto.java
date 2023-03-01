package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfPriority;
import com.emontazysta.enums.TypeOfStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDto {

    private Long id;
    private String name;
    private TypeOfStatus typeOfStatus;
    private LocalDateTime plannedStart;
    private LocalDateTime plannedEnd;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private TypeOfPriority typeOfPriority;
    private Long companyId;
    private Long managerId;
    private Long foremanId;
    private Long specialistId;
    private Long salesRepresentativeId;
    private Long locationId;
    private Long clientId;
    private List<Long> orderStages;
    private List<Long> attachments;
}
