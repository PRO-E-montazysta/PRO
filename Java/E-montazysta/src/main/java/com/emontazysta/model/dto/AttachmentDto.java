package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfAttachment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDto {

    private Long id;
    private String name;
    private String url;
    private String description;
    private TypeOfAttachment typeOfAttachment;
    private LocalDateTime createdAt;
    private Long toolTypeId;
    private Long commentId;
    private Long employeeId;
    private Long toolEventId;
    private Long orderId;
    private Long elementId;
    private Long orderStageId;
    private Long elementEventId;
}
