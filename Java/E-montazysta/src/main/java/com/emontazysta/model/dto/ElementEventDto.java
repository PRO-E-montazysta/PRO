package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ElementEventDto {

    private Long id;

    private LocalDateTime eventDate;
    private LocalDateTime movingDate;
    private LocalDateTime completionDate;
    private String description;
    private TypeOfStatus status;
    private int quantity;
    private Long acceptedById;
    private Long updatedById;
    private Long elementId;
    private List<Long> attachments;
}
