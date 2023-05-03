package com.emontazysta.model.dto;

import com.emontazysta.enums.OrderStageStatus;
import com.emontazysta.model.Tool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStageWithToolsAndElementsDto {

    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private OrderStageStatus status;
    @NotNull(message = "Price cannot be empty")
    private BigDecimal price;
    private LocalDateTime plannedStartDate;
    private LocalDateTime plannedEndDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @PositiveOrZero(message = "Planned duration time cannot be negative")
    private long plannedDurationTime;
    @PositiveOrZero(message = "Planned fitters number cannot be negative")
    private int plannedFittersNumber;
    @PositiveOrZero(message = "Minimum images number cannot be negative")
    private int minimumImagesNumber;
    private List<Long> fitters;
    private Long foremanId;
    private List<Long> comments;
    private List<ToolReleaseDto> toolReleases;
    private List<ElementReturnReleaseDto> elementReturnReleases;
    private Long orderId;
    private List<Long> attachments;
    private List<Long> notifications;
    private List<ToolsPlannedNumberDto> listOfToolsPlannedNumber;
    private List<ElementsPlannedNumberDto> listOfElementsPlannedNumber;
    private List<Long> demandAdHocs;
    private List<String> returningTools;
    private List<ElementReturnDto> returningElements;
}
