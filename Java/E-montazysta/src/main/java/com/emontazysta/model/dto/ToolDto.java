package com.emontazysta.model.dto;

import com.emontazysta.enums.ToolStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolDto {

    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private LocalDateTime createdAt;
    private String code;
    private List<Long> toolReleases;
    private Long warehouseId;
    private List<Long> toolEvents;
    private Long toolTypeId;
    private ToolStatus status;
    private boolean deleted;
}
