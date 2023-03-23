package com.emontazysta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    @NotBlank(message = "Content cannot be empty")
    private String content;
    private LocalDateTime createdAt;
    private Long messageCreatorId;
    private Long orderStageId;
    private List<Long> attachments;
}
