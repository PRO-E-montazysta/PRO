package com.emontazysta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
