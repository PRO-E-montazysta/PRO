package com.emontazysta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandAdHocDto {

    private Long id;
    @NotBlank(message = "Description cannot be empty")
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
    private List<Long> listOfToolsPlannedNumber;
    private List<Long> listOfElementsPlannedNumber;
}
