package com.emontazysta.mapper;

import com.emontazysta.model.ToolRelease;
import com.emontazysta.model.dto.ToolReleaseDto;
import com.emontazysta.repository.DemandAdHocRepository;
import com.emontazysta.repository.ForemanRepository;
import com.emontazysta.repository.OrderStageRepository;
import com.emontazysta.repository.ToolRepository;
import com.emontazysta.repository.WarehousemanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class ToolReleaseMapper {

    private final ForemanRepository foremanRepository;
    private final WarehousemanRepository warehousemanRepository;
    private final ToolRepository toolRepository;
    private final DemandAdHocRepository demandAdHocRepository;
    private final OrderStageRepository orderStageRepository;

    public ToolReleaseDto toDto(ToolRelease toolRelease) {
        return ToolReleaseDto.builder()
                .id(toolRelease.getId())
                .releaseTime(toolRelease.getReleaseTime())
                .returnTime(toolRelease.getReturnTime())
                .receivedById(toolRelease.getReceivedBy() == null ? null : toolRelease.getReceivedBy().getId())
                .releasedById(toolRelease.getReleasedBy() == null ? null : toolRelease.getReleasedBy().getId())
                .toolId(toolRelease.getTool() == null ? null : toolRelease.getTool().getId())
                .demandAdHocId(toolRelease.getDemandAdHoc() == null ? null : toolRelease.getDemandAdHoc().getId())
                .orderStageId(toolRelease.getOrderStage() == null ? null : toolRelease.getOrderStage().getId())
                .build();
    }

    public ToolRelease toEntity(ToolReleaseDto toolReleaseDto) {
        return ToolRelease.builder()
                .id(toolReleaseDto.getId())
                .releaseTime(toolReleaseDto.getReleaseTime())
                .returnTime(toolReleaseDto.getReturnTime())
                .receivedBy(toolReleaseDto.getReceivedById() == null ? null : foremanRepository.findById(toolReleaseDto.getReceivedById()).orElseThrow(EntityNotFoundException::new))
                .releasedBy(toolReleaseDto.getReleasedById() == null ? null : warehousemanRepository.findById(toolReleaseDto.getReleasedById()).orElseThrow(EntityNotFoundException::new))
                .tool(toolReleaseDto.getToolId() == null ? null : toolRepository.findById(toolReleaseDto.getToolId()).orElseThrow(EntityNotFoundException::new))
                .demandAdHoc(toolReleaseDto.getDemandAdHocId() == null ? null : demandAdHocRepository.findById(toolReleaseDto.getDemandAdHocId()).orElseThrow(EntityNotFoundException::new))
                .orderStage(toolReleaseDto.getOrderStageId() == null ? null : orderStageRepository.findById(toolReleaseDto.getOrderStageId()).orElseThrow(EntityNotFoundException::new))
                .build();
    }
}
