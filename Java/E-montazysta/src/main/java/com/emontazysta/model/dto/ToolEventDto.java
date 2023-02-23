package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfStatus;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
public class ToolEventDto {

    private Long id;
    private LocalDateTime eventDate;
    private LocalDateTime movingDate;
    private LocalDateTime completionDate;
    private String description;
    private TypeOfStatus status;
    private Long updatedById;
    private Long acceptedById;
    private Long toolId;
    private List<Long> attachments;
}
