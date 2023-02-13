package com.emontazysta.model.dto;

import com.emontazysta.model.ElementReturnRelease;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<Long> toolReleases;
    private List<Long> elementReturnReleases;
    private Long warehouseManagerId;
    private Long warehousemanId;
    private Long specialistId;
    private Long managerId;
    private Long foremanId;
    private List<Long> ordersStages;
}
