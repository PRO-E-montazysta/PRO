package com.emontazysta.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkingOnDto {
    private Long orderId;
    private String orderName;
    private String orderStageName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
