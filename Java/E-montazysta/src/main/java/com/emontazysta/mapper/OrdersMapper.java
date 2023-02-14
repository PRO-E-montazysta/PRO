package com.emontazysta.mapper;

import com.emontazysta.model.Attachment;
import com.emontazysta.model.OrderStage;
import com.emontazysta.model.Orders;
import com.emontazysta.model.dto.OrdersDto;
import com.emontazysta.repository.AttachmentRepository;
import com.emontazysta.repository.ClientRepository;
import com.emontazysta.repository.CompanyRepository;
import com.emontazysta.repository.ForemanRepository;
import com.emontazysta.repository.LocationRepository;
import com.emontazysta.repository.ManagerRepository;
import com.emontazysta.repository.OrderStageRepository;
import com.emontazysta.repository.SalesRepresentativeRepository;
import com.emontazysta.repository.SpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrdersMapper {

    private final CompanyRepository companyRepository;
    private final ManagerRepository managerRepository;
    private final ForemanRepository foremanRepository;
    private final SpecialistRepository specialistRepository;
    private final SalesRepresentativeRepository salesRepresentativeRepository;
    private final LocationRepository locationRepository;
    private final ClientRepository clientRepository;
    private final OrderStageRepository orderStageRepository;
    private final AttachmentRepository attachmentRepository;

    public OrdersDto toDto (Orders orders) {
        return OrdersDto.builder()
                .id(orders.getId())
                .name(orders.getName())
                .typeOfStatus(orders.getTypeOfStatus())
                .plannedStart(orders.getPlannedStart())
                .plannedEnd(orders.getPlannedEnd())
                .createdAt(orders.getCreatedAt())
                .editedAt(orders.getEditedAt())
                .typeOfPriority(orders.getTypeOfPriority())
                .companyId(orders.getCompany() == null ? null : orders.getCompany().getId())
                .managerId(orders.getManagedBy() == null ? null : orders.getManagedBy().getId())
                .foremanId(orders.getAssignedTo() == null ? null : orders.getAssignedTo().getId())
                .specialistId(orders.getSpecialist() == null ? null : orders.getSpecialist().getId())
                .salesRepresentativeId(orders.getSalesRepresentative() == null ? null : orders.getSalesRepresentative().getId())
                .locationId(orders.getLocation() == null ? null : orders.getLocation().getId())
                .clientId(orders.getClient() == null ? null : orders.getClient().getId())
                .orderStages(orders.getOrderStages().stream().map(OrderStage::getId).collect(Collectors.toList()))
                .attachments(orders.getAttachments().stream().map(Attachment::getId).collect(Collectors.toList()))
                .build();
    }

    public Orders toEntity(OrdersDto ordersDto) {

        List<OrderStage> orderStageList = new ArrayList<>();
        ordersDto.getOrderStages().forEach(orderStageId -> orderStageList.add(orderStageRepository.getReferenceById(orderStageId)));

        List<Attachment> attachmentList = new ArrayList<>();
        ordersDto.getAttachments().forEach(attachmentId -> attachmentList.add(attachmentRepository.getReferenceById(attachmentId)));

        return Orders.builder()
                .id(ordersDto.getId())
                .name(ordersDto.getName())
                .typeOfStatus(ordersDto.getTypeOfStatus())
                .plannedStart(ordersDto.getPlannedStart())
                .plannedEnd(ordersDto.getPlannedEnd())
                .createdAt(ordersDto.getCreatedAt())
                .editedAt(ordersDto.getEditedAt())
                .typeOfPriority(ordersDto.getTypeOfPriority())
                .company(ordersDto.getCompanyId() == null ? null : companyRepository.getReferenceById(ordersDto.getCompanyId()))
                .managedBy(ordersDto.getManagerId() == null ? null : managerRepository.getReferenceById(ordersDto.getManagerId()))
                .assignedTo(ordersDto.getForemanId() == null ? null : foremanRepository.getReferenceById(ordersDto.getForemanId()))
                .specialist(ordersDto.getSpecialistId() == null ? null : specialistRepository.getReferenceById(ordersDto.getSpecialistId()))
                .salesRepresentative(ordersDto.getSalesRepresentativeId() == null ? null : salesRepresentativeRepository.getReferenceById(ordersDto.getSalesRepresentativeId()))
                .location(ordersDto.getLocationId() == null ? null : locationRepository.getReferenceById(ordersDto.getLocationId()))
                .client(ordersDto.getClientId() == null ? null : clientRepository.getReferenceById(ordersDto.getClientId()))
                .orderStages(orderStageList)
                .attachments(attachmentList)
                .build();
    }
}
