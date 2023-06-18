package com.emontazysta.mapper;


import com.emontazysta.enums.OrderStageStatus;
import com.emontazysta.enums.OrderStatus;
import com.emontazysta.model.OrderStage;
import com.emontazysta.model.Orders;
import com.emontazysta.model.dto.WorkingOnDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class WorkingOnMapper {

    public WorkingOnDto foremanWorks(Orders assignedTo) {
        return WorkingOnDto.builder()
                .orderId(assignedTo.getId())
                .orderName(assignedTo.getName())
                .startDate(assignedTo.getStatus().equals(OrderStatus.IN_PROGRESS) || assignedTo.getStatus().equals(OrderStatus.FINISHED) ? assignedTo.getPlannedStart() : null)
                .endDate(assignedTo.getStatus().equals(OrderStatus.FINISHED) ? assignedTo.getPlannedEnd() : null)
                .build();
    }

    public WorkingOnDto fitterWorks(OrderStage assignedTo) {
        return WorkingOnDto.builder()
                .orderId(assignedTo.getOrders().getId())
                .orderName(assignedTo.getOrders().getName())
                .orderStageName(assignedTo.getName())
                .startDate(Arrays.asList(OrderStageStatus.ON_WORK, OrderStageStatus.RETURN, OrderStageStatus.RETURNED,
                        OrderStageStatus.FINISHED).contains(assignedTo.getStatus()) ? assignedTo.getStartDate() : null)
                .endDate(assignedTo.getStatus().equals(OrderStageStatus.FINISHED) ? assignedTo.getEndDate() : null)
                .build();
    }
}
