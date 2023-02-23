package com.emontazysta.model.dto;

import lombok.Data;
import lombok.Builder;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ToolDto {

    private Long id;
    private String name;
    private Date createdAt;
    private String code;
    private List<Long> toolReleases;
    private Long warehouseId;
    private List<Long> toolEvents;
    private Long toolTypeId;
}
