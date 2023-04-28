package com.emontazysta.model.dto.filterDto;

import com.emontazysta.model.dto.ElementsPlannedNumberDto;
import com.emontazysta.model.dto.ToolsPlannedNumberDto;
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
public class DemandAdHocWithToolsAndElementsDto {

    private Long id;
    @NotBlank(message = "Description cannot be empty")
    private String description;
    private LocalDateTime createdAt;
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
    private Long createdById;
    private Long orderStageId;
    private List<ToolsPlannedNumberDto> listOfToolsPlannedNumber;
    private List<ElementsPlannedNumberDto> listOfElementsPlannedNumber;
}
