package com.emontazysta.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
public class ManagerDto extends EmployeeDto {

    private List<Long> createdUnavailabilities;
    private List<Long> acceptedEvents;
    private List<Long> managedOrders;
    private List<Long> demandsAdHocs;
    private List<Long> elementEvents;
}
