package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfAttachment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDto {

    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private String url;
    private String description;
    @NotNull(message = "Type of attachment cannot be empty")
    private TypeOfAttachment typeOfAttachment;
    private LocalDateTime createdAt;
    private String fileName;
    private Long toolTypeId;
    private Long commentId;
    private Long employeeId;
    private Long toolEventId;
    private Long orderId;
    private Long elementId;
    private Long orderStageId;
    private Long elementEventId;
    private boolean deleted;
}
