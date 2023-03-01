package com.emontazysta.model.dto;

import com.emontazysta.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
