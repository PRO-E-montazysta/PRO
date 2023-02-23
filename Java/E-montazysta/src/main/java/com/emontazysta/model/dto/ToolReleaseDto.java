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
    private Long receivedById;
    private Long releasedById;
    private Long toolId;
    private Long demandAdHocId;
    private Long orderStageId;
}
