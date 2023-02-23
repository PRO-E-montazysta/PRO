package com.emontazysta.model.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class CommentDto {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long messageCreatorId;
    private Long orderStageId;
    private List<Long> attachments;
}
