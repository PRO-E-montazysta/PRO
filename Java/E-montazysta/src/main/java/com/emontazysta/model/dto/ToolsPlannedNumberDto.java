package com.emontazysta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolsPlannedNumberDto {

    private Long id;
    @Min(value = 1, message = "Number of tools should not be less than 1")
    private int numberOfTools;
    @NotNull(message = "Tool type cannot be empty")
    private Long toolTypeId;
    private Long orderStageId;
    private Long demandAdHocId;
}
