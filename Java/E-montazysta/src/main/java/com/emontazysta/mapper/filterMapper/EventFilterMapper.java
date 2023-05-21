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
                .createdById(toolEvent.getCreatedBy() == null ? null : toolEvent.getCreatedBy().getId())
                .acceptedById(toolEvent.getAcceptedBy() == null ? null : toolEvent.getAcceptedBy().getId())
                .itemName(toolEvent.getTool().getName())
                .eventType("T")
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
                .createdById(elementEvent.getCreatedBy() == null ? null : elementEvent.getCreatedBy().getId())
                .acceptedById(elementEvent.getAcceptedBy() == null ? null : elementEvent.getAcceptedBy().getId())
                .itemName(elementEvent.getElement().getName())
                .eventType("E")
                .build();
    }
}
