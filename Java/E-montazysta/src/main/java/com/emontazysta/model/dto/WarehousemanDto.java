package com.emontazysta.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class WarehousemanDto extends EmployeeDto {

    private List<Long> releaseTools;
    private List<Long> elementReturnReleases;
    private List<Long> demandAdHocs;
}
