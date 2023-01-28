package com.emontazysta.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@Data
public class DemandAdHocDto {

    private Long id;
    private String description;
    private String comments;
    private LocalDateTime creationTime;
    private LocalDateTime readByWarehousemanTime;
    private LocalDateTime realisationTime;
    private String warehousemanComment;
    private String specialistComment;
}
