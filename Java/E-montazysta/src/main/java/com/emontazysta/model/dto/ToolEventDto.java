package com.emontazysta.model.dto;

import com.emontazysta.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ToolEventDto {

    private Long id;
    private LocalDateTime eventDate;
    private LocalDateTime movingDate;
    private LocalDateTime completionDate;
    private String description;
    @NotNull(message = "Event status cannot be empty")
    private EventStatus status;
    private Long updatedById;
    private Long acceptedById;
    @NotNull(message = "Tool id cannot be empty")
    private Long toolId;
    private List<Long> attachments;
}
