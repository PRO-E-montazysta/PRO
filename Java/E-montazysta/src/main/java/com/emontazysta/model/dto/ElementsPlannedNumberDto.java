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
public class ElementsPlannedNumberDto {

    private Long id;
    @Min(value = 1, message = "Number of elements should not be less than 1")
    private int numberOfElements;
    @NotNull(message = "Element cannot be empty")
    private Long elementId;
    private Long orderStageId;
    private Long demandAdHocId;
}
