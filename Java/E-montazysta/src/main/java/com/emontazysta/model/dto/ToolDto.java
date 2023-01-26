package com.emontazysta.model.dto;

import lombok.Data;
import lombok.Builder;

import java.util.Date;

@Data
@Builder
public class ToolDto {

    private Long id;
    private String name;
    private Date createdAt;
    private String code;
}
