package com.emontazysta.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
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
