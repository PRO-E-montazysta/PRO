package com.emontazysta.mapper;

import com.emontazysta.model.ElementEvent;
import com.emontazysta.model.dto.ElementEventDto;

public class ElementEventMapper {

    public static ElementEventDto toDto(ElementEvent event){
        return ElementEventDto.builder()
                .id(event.getId())
                .eventDate(event.getEventDate())
                .movingDate(event.getMovingDate())
                .completionDate(event.getCompletionDate())
                .description(event.getDescription())
                .status(event.getStatus())
                .build();

    }
}
