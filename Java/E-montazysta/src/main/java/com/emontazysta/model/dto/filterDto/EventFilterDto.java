package com.emontazysta.model.dto.filterDto;

import com.emontazysta.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventFilterDto {

    private Long id;
    private LocalDateTime eventDate;
    private LocalDateTime movingDate;
    private LocalDateTime completionDate;
    private String description;
    private EventStatus status;
    private Long createdById;
    private Long acceptedById;
    private String itemName;
    private String eventType;
}
