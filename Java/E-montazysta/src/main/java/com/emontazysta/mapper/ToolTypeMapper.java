package com.emontazysta.mapper;

import com.emontazysta.model.ToolType;
import com.emontazysta.model.dto.ToolTypeDto;

public class ToolTypeMapper {

    public static ToolTypeDto toDto(ToolType toolType){
        return ToolTypeDto.builder()
                .id(toolType.getId())
                .name(toolType.getName())
                .inServiceCount(toolType.getInServiceCount())
                .criticalNumber(toolType.getCriticalNumber())
                .availableCount(toolType.getAvailableCount())
                .build();

    }
}
