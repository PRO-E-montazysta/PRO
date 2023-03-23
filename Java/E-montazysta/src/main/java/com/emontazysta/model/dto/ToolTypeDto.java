package com.emontazysta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolTypeDto {
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;
    private int inServiceCount;
    @PositiveOrZero(message = "Critical number cannot be negative")
    private int criticalNumber;
    private int availableCount;
    private List<Long> attachments;
    private List<Long> orderStages;
    private List<Long> tools;
}
