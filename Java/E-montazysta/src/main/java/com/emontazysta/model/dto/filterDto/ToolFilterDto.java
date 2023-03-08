package com.emontazysta.model.dto.filterDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolFilterDto {

    private Long id;
    private String name;
    private String code;
    private String warehouse;
    private String toolType;
}
