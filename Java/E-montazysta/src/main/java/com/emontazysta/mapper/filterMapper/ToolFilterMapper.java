package com.emontazysta.mapper.filterMapper;

import com.emontazysta.model.Tool;
import com.emontazysta.model.dto.filterDto.ToolFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToolFilterMapper {

    public ToolFilterDto toDto(Tool tool) {
        return ToolFilterDto.builder()
                .id(tool.getId())
                .name(tool.getName())
                .code(tool.getCode())
                .warehouse(tool.getWarehouse() == null ? null : tool.getWarehouse().getName())
                .toolType(tool.getToolType() == null ? null : tool.getToolType().getName())
                .build();
    }
}
