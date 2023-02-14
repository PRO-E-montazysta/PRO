package com.emontazysta.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class ForemanDto extends FitterDto {

    private List<Long> ordersStagesList;
    private List<Long> receivedTools;
    private List<Long> assignedOrders;
    private List<Long> elementReturnReleases;
    private List<Long> demandsAdHocs;
}
