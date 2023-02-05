package com.emontazysta.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ToolReleaseDto {

    private Long id;
    private LocalDateTime releaseTime;
    private LocalDateTime returnTime;
}
