package com.emontazysta.mapper;

import com.emontazysta.model.Attachment;
import com.emontazysta.model.Comment;
import com.emontazysta.model.DemandAdHoc;
import com.emontazysta.model.ElementEvent;
import com.emontazysta.model.Employment;
import com.emontazysta.model.Manager;
import com.emontazysta.model.Notification;
import com.emontazysta.model.Orders;
import com.emontazysta.model.ToolEvent;
import com.emontazysta.model.Unavailability;
import com.emontazysta.model.dto.ManagerDto;
import com.emontazysta.repository.AttachmentRepository;
import com.emontazysta.repository.CommentRepository;
import com.emontazysta.repository.DemandAdHocRepository;
import com.emontazysta.repository.ElementEventRepository;
import com.emontazysta.repository.EmploymentRepository;
import com.emontazysta.repository.NotificationRepository;
import com.emontazysta.repository.OrderRepository;
import com.emontazysta.repository.ToolEventRepository;
import com.emontazysta.repository.UnavailabilityRepository;
import com.emontazysta.service.EmploymentService;
import com.emontazysta.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ManagerMapper {

    private final UnavailabilityRepository unavailabilityRepository;
    private final NotificationRepository notificationRepository;
    private final CommentRepository commentRepository;
    private final ElementEventRepository elementEventRepository;
    private final EmploymentRepository employmentRepository;
    private final EmploymentService employmentService;
    private final AttachmentRepository attachmentRepository;
    private final ToolEventRepository toolEventRepository;
    private final OrderRepository orderRepository;
    private final DemandAdHocRepository demandAdHocRepository;
    private final StatusService statusService;

    public ManagerDto toDto(Manager manager) {

        return ManagerDto.builder()
                .id(manager.getId())
                .firstName(manager.getFirstName())
                .lastName(manager.getLastName())
                .username(manager.getUsername())
                .roles(manager.getRoles())
                .email(manager.getEmail())
                .phone(manager.getPhone())
                .pesel(manager.getPesel())
                .unavailabilities(manager.getUnavailabilities().stream()
                        .map(Unavailability::getId)
                        .collect(Collectors.toList()))
                .notifications(manager.getNotifications().stream()
                        .map(Notification::getId)
                        .collect(Collectors.toList()))
                .employeeComments(manager.getEmployeeComments().stream()
                        .map(Comment::getId)
                        .collect(Collectors.toList()))
                .elementEvents(manager.getElementEvents().stream()
                        .map(ElementEvent::getId)
                        .collect(Collectors.toList()))
                .employments(manager.getEmployments().stream()
                        .map(Employment::getId)
                        .collect(Collectors.toList()))
                .attachments(manager.getAttachments().stream()
                        .map(Attachment::getId)
                        .collect(Collectors.toList()))
                .toolEvents(manager.getToolEvents().stream()
                        .map(ToolEvent::getId)
                        .collect(Collectors.toList()))
                .createdUnavailabilities(manager.getCreatedUnavailabilities().stream()
                        .map(Unavailability::getId)
                        .collect(Collectors.toList()))
                .acceptedEvents(manager.getAcceptedEvents().stream()
                        .map(ToolEvent::getId)
                        .collect(Collectors.toList()))
                .managedOrders(manager.getManagedOrders().stream()
                        .map(Orders::getId)
                        .collect(Collectors.toList()))
                .status(statusService.checkUnavailability(manager) == null ? "AVAILABLE" : String.valueOf(statusService.checkUnavailability(manager).getTypeOfUnavailability()))
                .unavailableFrom(statusService.checkUnavailability(manager) == null ? null : statusService.checkUnavailability(manager).getUnavailableFrom())
                .unavailableTo(statusService.checkUnavailability(manager) == null ? null : statusService.checkUnavailability(manager).getUnavailableTo())
                .unavailbilityDescription(statusService.checkUnavailability(manager) == null ? null : statusService.checkUnavailability(manager).getDescription())
                .deleted(manager.isDeleted())
                .active(employmentService.getCurrentEmploymentByEmployeeId(manager.getId()).isPresent())
                .build();
    }

    public Manager toEntity(ManagerDto managerDto) {

        List<Unavailability> unavailabilityList = new ArrayList<>();
        managerDto.getUnavailabilities().forEach(unavailabilityId -> unavailabilityList.add(unavailabilityRepository.findById(unavailabilityId).orElseThrow(EntityNotFoundException::new)));

        List<Notification> notificationList = new ArrayList<>();
        managerDto.getNotifications().forEach(notificationId -> notificationList.add(notificationRepository.findById(notificationId).orElseThrow(EntityNotFoundException::new)));

        List<Comment> employeeComments = new ArrayList<>();
        managerDto.getEmployeeComments().forEach(employeeCommentId -> employeeComments.add(commentRepository.findById(employeeCommentId).orElseThrow(EntityNotFoundException::new)));

        List<ElementEvent> elementEventList = new ArrayList<>();
        managerDto.getElementEvents().forEach(elementEventId -> elementEventList.add(elementEventRepository.findById(elementEventId).orElseThrow(EntityNotFoundException::new)));

        List<Employment> employmentList = new ArrayList<>();
        managerDto.getEmployments().forEach(employmentId -> employmentList.add(employmentRepository.findById(employmentId).orElseThrow(EntityNotFoundException::new)));

        List<Attachment> attachmentList = new ArrayList<>();
        managerDto.getAttachments().forEach(attachmentId -> attachmentList.add(attachmentRepository.findById(attachmentId).orElseThrow(EntityNotFoundException::new)));

        List<ToolEvent> toolEventList = new ArrayList<>();
        managerDto.getToolEvents().forEach(toolEventId -> toolEventList.add(toolEventRepository.findById(toolEventId).orElseThrow(EntityNotFoundException::new)));

        List<Unavailability> createdUnavailabilities = new ArrayList<>();
        managerDto.getCreatedUnavailabilities().forEach(unavailabilityId -> createdUnavailabilities.add(unavailabilityRepository.findById(unavailabilityId).orElseThrow(EntityNotFoundException::new)));

        List<ToolEvent> acceptedEvents = new ArrayList<>();
        managerDto.getAcceptedEvents().forEach(acceptedEventId -> acceptedEvents.add(toolEventRepository.findById(acceptedEventId).orElseThrow(EntityNotFoundException::new)));

        List<Orders> ordersList = new ArrayList<>();
        managerDto.getManagedOrders().forEach(orderId -> ordersList.add(orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new)));

        List<ElementEvent> elementEvents = new ArrayList<>();
        managerDto.getElementEvents().forEach(elementEventId -> elementEvents.add(elementEventRepository.findById(elementEventId).orElseThrow(EntityNotFoundException::new)));

        Manager manager = new Manager();
        manager.setId(managerDto.getId());
        manager.setFirstName(managerDto.getFirstName());
        manager.setLastName(managerDto.getLastName());
        manager.setEmail(managerDto.getEmail());
        manager.setUsername(managerDto.getUsername());
        manager.setRoles(managerDto.getRoles());
        manager.setPassword(managerDto.getPassword());
        manager.setPhone(managerDto.getPhone());
        manager.setPesel(managerDto.getPesel());
        manager.setUnavailabilities(unavailabilityList);
        manager.setNotifications(notificationList);
        manager.setEmployeeComments(employeeComments);
        manager.setElementEvents(elementEventList);
        manager.setEmployments(employmentList);
        manager.setAttachments(attachmentList);
        manager.setToolEvents(toolEventList);
        manager.setCreatedUnavailabilities(createdUnavailabilities);
        manager.setAcceptedEvents(acceptedEvents);
        manager.setManagedOrders(ordersList);
        manager.setElementEvents(elementEvents);

        return manager;
    }
}
