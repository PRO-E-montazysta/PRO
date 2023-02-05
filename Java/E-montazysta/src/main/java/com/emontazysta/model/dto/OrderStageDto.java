package com.emontazysta.model.dto;

import com.emontazysta.model.OrderStatus;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderStageDto {

    private Long id;
    private String name;
    private OrderStatus status;
    private BigDecimal price;
    private String order;
    private LocalDate plannedEndDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private long plannedDurationTime;
    private int plannedFittersNumber;
    private int minimumImagesNumber;
}
