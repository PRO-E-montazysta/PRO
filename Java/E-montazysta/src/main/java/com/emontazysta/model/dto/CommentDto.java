package com.emontazysta.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Builder
@Data
public class CommentDto {

    private Long id;
    private String content;
    private Date createdAt;
    private Long messageCreatorId;
    private Long orderStageId;
    private List<Long> attachments;
}
