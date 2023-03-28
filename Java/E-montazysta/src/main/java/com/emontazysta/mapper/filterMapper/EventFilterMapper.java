package com.emontazysta.mapper.filterMapper;

import com.emontazysta.model.ElementEvent;
import com.emontazysta.model.ToolEvent;
import com.emontazysta.model.dto.filterDto.EventFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventFilterMapper {

    public EventFilterDto toolToDto(ToolEvent toolEvent) {
        return EventFilterDto.builder()
                .id(toolEvent.getId())
                .eventDate(toolEvent.getEventDate())
                .movingDate(toolEvent.getMovingDate())
                .completionDate(toolEvent.getCompletionDate())
                .description(toolEvent.getDescription())
                .status(toolEvent.getStatus())
                .updatedById(toolEvent.getUpdatedBy() == null ? null : toolEvent.getUpdatedBy().getId())
                .acceptedById(toolEvent.getUpdatedBy() == null ? null : toolEvent.getUpdatedBy().getId())
                .itemName(toolEvent.getTool().getName())
                .eventType("TOOL")
                .build();
    }

    public EventFilterDto elementToDto(ElementEvent elementEvent) {
        return EventFilterDto.builder()
                .id(elementEvent.getId())
                .eventDate(elementEvent.getEventDate())
                .movingDate(elementEvent.getMovingDate())
                .completionDate(elementEvent.getCompletionDate())
                .description(elementEvent.getDescription())
                .status(elementEvent.getStatus())
                .updatedById(elementEvent.getUpdatedBy() == null ? null : elementEvent.getUpdatedBy().getId())
                .acceptedById(elementEvent.getUpdatedBy() == null ? null : elementEvent.getUpdatedBy().getId())
                .itemName(elementEvent.getElement().getName())
                .eventType("ELEMENT")
                .build();
    }
}
