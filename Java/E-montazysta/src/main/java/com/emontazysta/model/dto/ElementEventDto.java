package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
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
    @NotNull(message = "Status cannot be empty")
    private TypeOfStatus status;
    @PositiveOrZero(message = "Quantity cannot be negative")
    private int quantity;
    private Long acceptedById;
    private Long updatedById;
    @NotNull(message = "Element id cannot be empty")
    private Long elementId;
    private List<Long> attachments;
}
