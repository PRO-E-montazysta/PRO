package com.emontazysta.mapper;

import com.emontazysta.model.ToolEvent;
import com.emontazysta.model.dto.ToolEventDto;

public class ToolEventMapper {
    public static ToolEventDto toDto(ToolEvent event){
        return ToolEventDto.builder()
                .id(event.getId())
                .eventDate(event.getEventDate())
                .movingDate(event.getEventDate())
                .completionDate(event.getCompletionDate())
                .description(event.getDescription())
                .status(event.getStatus())
                .build();

    }
}
