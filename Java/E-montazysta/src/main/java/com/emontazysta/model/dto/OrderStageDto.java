package com.emontazysta.model.dto;

import com.emontazysta.model.OrderStatus;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderStageDto {

    private Long id;
    private String name;
    private OrderStatus status;
    private BigDecimal price;
    private Integer order;
    private LocalDate plannedEndDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private long plannedDurationTime;
    private int plannedFittersNumber;
    private int minimumImagesNumber;
    private List<Long> fitters;
    private Long foremanId;
    private List<Long> comments;
    private List<Long> toolReleases;
    private List<Long> elementReturnReleases;
    private Long orderId;
    private List<Long> attachments;
    private List<Long> notifications;
    private List<Long> tools;
    private List<Long> elements;
    private List<Long> demandAdHocs;
}
