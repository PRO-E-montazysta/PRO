package com.emontazysta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolTypeDto {
    private Long id;

    private String name;

    private int inServiceCount;

    private int criticalNumber;

    private int availableCount;
    private List<Long> attachments;
    private List<Long> orderStages;
    private List<Long> tools;
}
