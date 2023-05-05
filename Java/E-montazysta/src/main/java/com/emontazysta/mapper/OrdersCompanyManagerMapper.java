package com.emontazysta.mapper;

import com.emontazysta.model.Attachment;
import com.emontazysta.model.OrderStage;
import com.emontazysta.model.Orders;
import com.emontazysta.model.dto.OrdersCompanyManagerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrdersCompanyManagerMapper {

    public OrdersCompanyManagerDto toDto (Orders orders) {
        return OrdersCompanyManagerDto.builder()
                .id(orders.getId())
                .name(orders.getName())
                .status(orders.getStatus())
                .plannedStart(orders.getPlannedStart())
                .plannedEnd(orders.getPlannedEnd())
                .createdAt(orders.getCreatedAt())
                .editedAt(orders.getEditedAt())
                .typeOfPriority(orders.getTypeOfPriority())
                .companyId(orders.getCompany() == null ? null : orders.getCompany().getId())
                .companyName(orders.getCompany() == null ? null : orders.getCompany().getCompanyName())
                .managerId(orders.getManagedBy() == null ? null : orders.getManagedBy().getId())
                .managerFirstName(orders.getManagedBy() == null ? null : orders.getManagedBy().getFirstName())
                .managerLastName(orders.getManagedBy() == null ? null : orders.getManagedBy().getLastName())
                .foremanId(orders.getAssignedTo() == null ? null : orders.getAssignedTo().getId())
                .specialistId(orders.getSpecialist() == null ? null : orders.getSpecialist().getId())
                .salesRepresentativeId(orders.getSalesRepresentative() == null ? null : orders.getSalesRepresentative().getId())
                .locationId(orders.getLocation() == null ? null : orders.getLocation().getId())
                .clientId(orders.getClient() == null ? null : orders.getClient().getId())
                .orderStages(orders.getOrderStages().stream().map(OrderStage::getId).collect(Collectors.toList()))
                .attachments(orders.getAttachments().stream().map(Attachment::getId).collect(Collectors.toList()))
                .build();

}
}
