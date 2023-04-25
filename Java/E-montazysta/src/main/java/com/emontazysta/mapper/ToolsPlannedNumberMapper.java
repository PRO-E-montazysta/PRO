package com.emontazysta.mapper;

import com.emontazysta.model.ToolsPlannedNumber;
import com.emontazysta.model.dto.ToolsPlannedNumberDto;
import com.emontazysta.repository.OrderStageRepository;
import com.emontazysta.repository.ToolTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class ToolsPlannedNumberMapper {

    private final ToolTypeRepository toolTypeRepository;

    private final OrderStageRepository orderStageRepository;

    public ToolsPlannedNumberDto toDto (ToolsPlannedNumber toolsPlannedNumber) {
        return ToolsPlannedNumberDto.builder()
                .id(toolsPlannedNumber.getId())
                .numberOfTools(toolsPlannedNumber.getNumberOfTools())
                .toolTypeId(toolsPlannedNumber.getToolType() == null ? null : toolsPlannedNumber.getToolType().getId())
                .orderStageId(toolsPlannedNumber.getOrderStage() == null ? null : toolsPlannedNumber.getOrderStage().getId())
                .build();
    }

    public ToolsPlannedNumber toEntity (ToolsPlannedNumberDto toolsPlannedNumberDto) {
        return ToolsPlannedNumber.builder()
                .id(toolsPlannedNumberDto.getId())
                .numberOfTools(toolsPlannedNumberDto.getNumberOfTools())
                .toolType(toolsPlannedNumberDto.getToolTypeId() == null ? null : toolTypeRepository.findById(toolsPlannedNumberDto.getToolTypeId()).orElseThrow(EntityNotFoundException::new))
                .orderStage(toolsPlannedNumberDto.getOrderStageId() == null ? null : orderStageRepository.findById(toolsPlannedNumberDto.getOrderStageId()).orElseThrow(EntityNotFoundException::new))
                .build();
    }
}
