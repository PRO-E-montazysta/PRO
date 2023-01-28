package com.emontazysta.mapper;

import com.emontazysta.model.Tool;
import com.emontazysta.model.dto.ToolDto;

public class ToolMapper {

    public static ToolDto toolToDto (Tool tool) {
        return ToolDto.builder()
                .id(tool.getId())
                .name(tool.getName())
                .createdAt(tool.getCreatedAt())
                .code(tool.getCode())
                .build();
    }
}
