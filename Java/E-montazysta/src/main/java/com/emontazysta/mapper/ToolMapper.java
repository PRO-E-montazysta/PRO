package com.emontazysta.mapper;

import com.emontazysta.model.Tool;
import com.emontazysta.model.ToolEvent;
import com.emontazysta.model.ToolRelease;
import com.emontazysta.model.dto.ToolDto;
import com.emontazysta.repository.ToolEventRepository;
import com.emontazysta.repository.ToolReleaseRepository;
import com.emontazysta.repository.ToolTypeRepository;
import com.emontazysta.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ToolMapper {

    private final ToolReleaseRepository toolReleaseRepository;
    private final WarehouseRepository warehouseRepository;
    private final ToolEventRepository toolEventRepository;
    private final ToolTypeRepository toolTypeRepository;

    public ToolDto toDto(Tool tool) {
        return ToolDto.builder()
                .id(tool.getId())
                .name(tool.getName())
                .createdAt(tool.getCreatedAt())
                .code(tool.getCode())
                .toolReleases(tool.getToolReleases().stream().map(ToolRelease::getId).collect(Collectors.toList()))
                .warehouseId(tool.getWarehouse() == null ? null : tool.getWarehouse().getId())
                .toolEvents(tool.getToolEvents().stream().map(ToolEvent::getId).collect(Collectors.toList()))
                .toolTypeId(tool.getToolType() == null ? null : tool.getToolType().getId())
                .build();
    }

    public Tool toEntity(ToolDto toolDto) {

        List<ToolRelease> toolReleaseList = new ArrayList<>();
        toolDto.getToolReleases().forEach(toolReleaseId -> toolReleaseList.add(toolReleaseRepository.getReferenceById(toolReleaseId)));

        List<ToolEvent> toolEventList = new ArrayList<>();
        toolDto.getToolEvents().forEach(toolEventId -> toolEventList.add(toolEventRepository.getReferenceById(toolEventId)));

        return Tool.builder()
                .id(toolDto.getId())
                .name(toolDto.getName())
                .createdAt(toolDto.getCreatedAt())
                .code(toolDto.getCode())
                .toolReleases(toolReleaseList)
                .warehouse(toolDto.getWarehouseId() == null ? null : warehouseRepository.getReferenceById(toolDto.getWarehouseId()))
                .toolEvents(toolEventList)
                .toolType(toolDto.getToolTypeId() == null ? null : toolTypeRepository.getReferenceById(toolDto.getToolTypeId()))
                .build();
    }
}
