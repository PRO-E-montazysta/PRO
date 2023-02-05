package com.emontazysta.mapper;

import com.emontazysta.model.OrderStage;
import com.emontazysta.model.dto.OrderStageDto;

public class OrderStageMapper {

    public static OrderStageDto orderStageToDto (OrderStage orderStage) {
        return OrderStageDto.builder()
                .id(orderStage.getId())
                .name(orderStage.getName())
                .status(orderStage.getStatus())
                .price(orderStage.getPrice())
                .order(orderStage.getOrder())
                .plannedEndDate(orderStage.getPlannedEndDate())
                .startDate(orderStage.getStartDate())
                .endDate(orderStage.getEndDate())
                .plannedDurationTime(orderStage.getPlannedDurationTime())
                .plannedFittersNumber(orderStage.getPlannedFittersNumber())
                .minimumImagesNumber(orderStage.getMinimumImagesNumber())
                .build();
    }
}
