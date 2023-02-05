package com.emontazysta.mapper;

import com.emontazysta.model.Orders;
import com.emontazysta.model.dto.OrdersDto;

public class OrdersMapper {

    public static OrdersDto ordersToDto (Orders orders) {
        return OrdersDto.builder()
                .id(orders.getId())
                .name(orders.getName())
                .typeOfStatus(orders.getTypeOfStatus())
                .plannedStart(orders.getPlannedStart())
                .plannedEnd(orders.getPlannedEnd())
                .createdAt(orders.getCreatedAt())
                .editedAt(orders.getEditedAt())
                .typeOfPriority(orders.getTypeOfPriority())
                .build();
    }
}
