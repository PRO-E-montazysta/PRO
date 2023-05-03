package com.emontazysta.model.dto;

import com.emontazysta.validation.IsAfter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IsAfter(startDateFieldName = "releaseTime", endDateFieldName = "returnTime")
public class ToolReleaseDto {

    private Long id;
    private LocalDateTime releaseTime;
    private LocalDateTime returnTime;
    private Long releasedById;
    @NotNull(message = "Tool id cannot be empty")
    private Long toolId;
    private Long demandAdHocId;
    private Long orderStageId;
}
