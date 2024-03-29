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

import javax.persistence.EntityNotFoundException;
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
                .status(orders.getStatus())
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
                .orderStages(orders.getOrderStages().stream()
                        .map(OrderStage::getId)
                        .collect(Collectors.toList()))
                .attachments(orders.getAttachments().stream()
                        .map(Attachment::getId)
                        .collect(Collectors.toList()))
                .deleted(orders.isDeleted())
                .build();
    }

    public Orders toEntity(OrdersDto ordersDto) {

        List<OrderStage> orderStageList = new ArrayList<>();
        ordersDto.getOrderStages().forEach(orderStageId -> orderStageList.add(orderStageRepository.findById(orderStageId).orElseThrow(EntityNotFoundException::new)));

        List<Attachment> attachmentList = new ArrayList<>();
        ordersDto.getAttachments().forEach(attachmentId -> attachmentList.add(attachmentRepository.findById(attachmentId).orElseThrow(EntityNotFoundException::new)));

        return Orders.builder()
                .id(ordersDto.getId())
                .name(ordersDto.getName())
                .status(ordersDto.getStatus())
                .plannedStart(ordersDto.getPlannedStart())
                .plannedEnd(ordersDto.getPlannedEnd())
                .createdAt(ordersDto.getCreatedAt())
                .editedAt(ordersDto.getEditedAt())
                .typeOfPriority(ordersDto.getTypeOfPriority())
                .company(ordersDto.getCompanyId() == null ? null : companyRepository.findById(ordersDto.getCompanyId()).orElseThrow(EntityNotFoundException::new))
                .managedBy(ordersDto.getManagerId() == null ? null : managerRepository.findById(ordersDto.getManagerId()).orElseThrow(EntityNotFoundException::new))
                .assignedTo(ordersDto.getForemanId() == null ? null : foremanRepository.findById(ordersDto.getForemanId()).orElseThrow(EntityNotFoundException::new))
                .specialist(ordersDto.getSpecialistId() == null ? null : specialistRepository.findById(ordersDto.getSpecialistId()).orElseThrow(EntityNotFoundException::new))
                .salesRepresentative(ordersDto.getSalesRepresentativeId() == null ? null : salesRepresentativeRepository.findById(ordersDto.getSalesRepresentativeId()).orElseThrow(EntityNotFoundException::new))
                .location(ordersDto.getLocationId() == null ? null : locationRepository.findById(ordersDto.getLocationId()).orElseThrow(EntityNotFoundException::new))
                .client(ordersDto.getClientId() == null ? null : clientRepository.findById(ordersDto.getClientId()).orElseThrow(EntityNotFoundException::new))
                .orderStages(orderStageList)
                .attachments(attachmentList)
                .build();
    }
}
