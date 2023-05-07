package com.emontazysta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    private Long id;
    @NotBlank(message = "Content cannot be empty")
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;
    private Long createdById;
    private List<Long> notifiedEmployees;
    private Long orderStageId;
    private boolean deleted;
}
