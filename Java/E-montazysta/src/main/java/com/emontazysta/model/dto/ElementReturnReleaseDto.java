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
public class ElementReturnReleaseDto {

    private Long id;
    private LocalDateTime releaseTime;
    private int releasedQuantity;
    private int returnedQuantity;
    private LocalDateTime returnTime;
    private Long servedById;
    private Long elementId;
    private Long demandAdHocId;
    private Long foremanId;
    private Long orderStageId;
}
