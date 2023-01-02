package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfStatus;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ElementEventDto {

    private Long id;

    private LocalDateTime eventDate;
    private LocalDateTime movingDate;
    private LocalDateTime completionDate;
    private String description;
    private TypeOfStatus status;
    private int quantity;
}
