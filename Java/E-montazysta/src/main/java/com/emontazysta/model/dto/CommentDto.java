package com.emontazysta.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class CommentDto {

    private Long id;
    private String content;
    private Date createdAt;
}
