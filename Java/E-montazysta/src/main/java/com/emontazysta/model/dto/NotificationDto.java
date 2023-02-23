package com.emontazysta.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class NotificationDto {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;
    private Long createdById;
    private List<Long> notifiedEmployees;
    private Long orderStageId;
}
