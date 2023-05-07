package com.emontazysta.model.dto;

import com.emontazysta.enums.OrderStatus;
import com.emontazysta.enums.TypeOfPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDto {

    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private OrderStatus status;
    @NotNull(message = "Planned start cannot be empty")
    private LocalDateTime plannedStart;
    @NotNull(message = "Planned end cannot be empty")
    private LocalDateTime plannedEnd;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    @NotNull(message = "Type of priority cannot be empty")
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
