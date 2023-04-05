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
    private final AttachmentRepository attachmentRepository;
    private final ToolEventRepository toolEventRepository;
    private final OrderRepository orderRepository;
    private final DemandAdHocRepository demandAdHocRepository;

    public ManagerDto toDto(Manager manager) {

        return ManagerDto.builder()
                .id(manager.getId())
                .firstName(manager.getFirstName())
                .lastName(manager.getLastName())
                .username(manager.getUsername())
                .email(manager.getEmail())
                .phone(manager.getPhone())
                .pesel(manager.getPesel())
                .unavailabilities(manager.getUnavailabilities().stream()
                        .filter(unavailability -> !unavailability.isDeleted())
                        .map(Unavailability::getId)
                        .collect(Collectors.toList()))
                .notifications(manager.getNotifications().stream()
                        .filter(notification -> !notification.isDeleted())
                        .map(Notification::getId)
                        .collect(Collectors.toList()))
                .employeeComments(manager.getEmployeeComments().stream()
                        .filter(comment -> !comment.isDeleted())
                        .map(Comment::getId)
                        .collect(Collectors.toList()))
                .elementEvents(manager.getElementEvents().stream()
                        .filter(elementEvent -> !elementEvent.isDeleted())
                        .map(ElementEvent::getId)
                        .collect(Collectors.toList()))
                .employments(manager.getEmployments().stream()
                        .filter(employment -> !employment.isDeleted())
                        .map(Employment::getId)
                        .collect(Collectors.toList()))
                .attachments(manager.getAttachments().stream()
                        .filter(attachment -> !attachment.isDeleted())
                        .map(Attachment::getId)
                        .collect(Collectors.toList()))
                .toolEvents(manager.getToolEvents().stream()
                        .filter(toolEvent -> !toolEvent.isDeleted())
                        .map(ToolEvent::getId)
                        .collect(Collectors.toList()))
                .createdUnavailabilities(manager.getCreatedUnavailabilities().stream()
                        .filter(unavailability -> !unavailability.isDeleted())
                        .map(Unavailability::getId)
                        .collect(Collectors.toList()))
                .acceptedEvents(manager.getAcceptedEvents().stream()
                        .filter(event -> !event.isDeleted())
                        .map(ToolEvent::getId)
                        .collect(Collectors.toList()))
                .managedOrders(manager.getManagedOrders().stream()
                        .filter(order -> !order.isDeleted())
                        .map(Orders::getId)
                        .collect(Collectors.toList()))
                .demandsAdHocs(manager.getDemandsAdHocs().stream()
                        .filter(demandAdHoc -> !demandAdHoc.isDeleted())
                        .map(DemandAdHoc::getId)
                        .collect(Collectors.toList()))
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

        List<DemandAdHoc> demandAdHocList = new ArrayList<>();
        managerDto.getDemandsAdHocs().forEach(demandAdHocId -> demandAdHocList.add(demandAdHocRepository.findById(demandAdHocId).orElseThrow(EntityNotFoundException::new)));

        List<ElementEvent> elementEvents = new ArrayList<>();
        managerDto.getElementEvents().forEach(elementEventId -> elementEvents.add(elementEventRepository.findById(elementEventId).orElseThrow(EntityNotFoundException::new)));

        Manager manager = new Manager();
        manager.setId(managerDto.getId());
        manager.setFirstName(managerDto.getFirstName());
        manager.setLastName(managerDto.getLastName());
        manager.setEmail(managerDto.getEmail());
        manager.setUsername(managerDto.getUsername());
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
        manager.setDemandsAdHocs(demandAdHocList);
        manager.setElementEvents(elementEvents);

        return manager;
    }
}
