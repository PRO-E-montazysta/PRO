package com.emontazysta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDto {

    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private String description;
    @NotBlank(message = "Opening hours cannot be empty")
    private String openingHours;
    private Long companyId;
    private Long locationId;
    private List<Long> elementInWarehouses;
    private List<Long> tools;
    private boolean deleted;
}
