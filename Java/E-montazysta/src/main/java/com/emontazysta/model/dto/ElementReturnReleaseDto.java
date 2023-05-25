package com.emontazysta.model.dto;

import com.emontazysta.validation.IsAfter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IsAfter(startDateFieldName = "releaseTime", endDateFieldName = "returnTime")
public class ElementReturnReleaseDto {

    private Long id;
    private LocalDateTime releaseTime;
    @PositiveOrZero(message = "Quantity cannot be negative")
    private int releasedQuantity;
    @PositiveOrZero(message = "Quantity cannot be negative")
    private int returnedQuantity;
    private LocalDateTime returnTime;
    private Long releasedById;
    @NotNull(message = "Element id cannot be empty")
    private Long elementId;
    private Long demandAdHocId;
    private Long orderStageId;
    private boolean deleted;
}
