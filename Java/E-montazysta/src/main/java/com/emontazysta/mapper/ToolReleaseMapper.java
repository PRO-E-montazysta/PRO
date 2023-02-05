package com.emontazysta.mapper;

import com.emontazysta.model.ToolRelease;
import com.emontazysta.model.dto.ToolReleaseDto;

public class ToolReleaseMapper {

    public static ToolReleaseDto toolReleaseToDto (ToolRelease toolRelease) {
        return ToolReleaseDto.builder()
                .id(toolRelease.getId())
                .releaseTime(toolRelease.getReleaseTime())
                .returnTime(toolRelease.getReturnTime())
                .build();
    }
}
