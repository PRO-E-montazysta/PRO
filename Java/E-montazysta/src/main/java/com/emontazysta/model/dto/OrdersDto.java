package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfPriority;
import com.emontazysta.enums.TypeOfStatus;
import lombok.Builder;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrdersDto {

    private Long id;
    private String name;
    private TypeOfStatus typeOfStatus;
    private Date plannedStart;
    private Date plannedEnd;
    private Date createdAt;
    private Date editedAt;
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
