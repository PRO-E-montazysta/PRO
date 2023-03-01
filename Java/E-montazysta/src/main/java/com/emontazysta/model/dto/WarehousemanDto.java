package com.emontazysta.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
public class WarehousemanDto extends EmployeeDto {

    private List<Long> releaseTools;
    private List<Long> elementReturnReleases;
    private List<Long> demandAdHocs;
}
