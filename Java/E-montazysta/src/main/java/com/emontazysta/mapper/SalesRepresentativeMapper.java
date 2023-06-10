package com.emontazysta.mapper;

import com.emontazysta.model.Attachment;
import com.emontazysta.model.Comment;
import com.emontazysta.model.ElementEvent;
import com.emontazysta.model.Employment;
import com.emontazysta.model.Notification;
import com.emontazysta.model.Orders;
import com.emontazysta.model.SalesRepresentative;
import com.emontazysta.model.ToolEvent;
import com.emontazysta.model.Unavailability;
import com.emontazysta.model.dto.SalesRepresentativeDto;
import com.emontazysta.repository.AttachmentRepository;
import com.emontazysta.repository.CommentRepository;
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
public class SalesRepresentativeMapper {

    private final UnavailabilityRepository unavailabilityRepository;
    private final NotificationRepository notificationRepository;
    private final CommentRepository commentRepository;
    private final ElementEventRepository elementEventRepository;
    private final EmploymentRepository employmentRepository;
    private final EmploymentService employmentService;
    private final AttachmentRepository attachmentRepository;
    private final ToolEventRepository toolEventRepository;
    private final OrderRepository orderRepository;
    private final StatusService statusService;

    public SalesRepresentativeDto toDto(SalesRepresentative salesRepresentative) {

        return SalesRepresentativeDto.builder()
                .id(salesRepresentative.getId())
                .firstName(salesRepresentative.getFirstName())
                .lastName(salesRepresentative.getLastName())
                .username(salesRepresentative.getUsername())
                .roles(salesRepresentative.getRoles())
                .email(salesRepresentative.getEmail())
                .phone(salesRepresentative.getPhone())
                .pesel(salesRepresentative.getPesel())
                .unavailabilities(salesRepresentative.getUnavailabilities().stream()
                        .map(Unavailability::getId)
                        .collect(Collectors.toList()))
                .notifications(salesRepresentative.getNotifications().stream()
                        .map(Notification::getId)
                        .collect(Collectors.toList()))
                .employeeComments(salesRepresentative.getEmployeeComments().stream()
                        .map(Comment::getId)
                        .collect(Collectors.toList()))
                .elementEvents(salesRepresentative.getElementEvents().stream()
                        .map(ElementEvent::getId)
                        .collect(Collectors.toList()))
                .employments(salesRepresentative.getEmployments().stream()
                        .map(Employment::getId)
                        .collect(Collectors.toList()))
                .attachments(salesRepresentative.getAttachments().stream()
                        .map(Attachment::getId)
                        .collect(Collectors.toList()))
                .toolEvents(salesRepresentative.getToolEvents().stream()
                        .map(ToolEvent::getId)
                        .collect(Collectors.toList()))
                .orders(salesRepresentative.getOrders().stream()
                        .map(Orders::getId)
                        .collect(Collectors.toList()))
                .status(statusService.checkUnavailability(salesRepresentative) == null ? "AVAILABLE" : String.valueOf(statusService.checkUnavailability(salesRepresentative).getTypeOfUnavailability()))
                .unavailableFrom(statusService.checkUnavailability(salesRepresentative) == null ? null : statusService.checkUnavailability(salesRepresentative).getUnavailableFrom())
                .unavailableTo(statusService.checkUnavailability(salesRepresentative) == null ? null : statusService.checkUnavailability(salesRepresentative).getUnavailableTo())
                .unavailbilityDescription(statusService.checkUnavailability(salesRepresentative) == null ? null : statusService.checkUnavailability(salesRepresentative).getDescription())
                .deleted(salesRepresentative.isDeleted())
                .active(employmentService.getCurrentEmploymentByEmployeeId(salesRepresentative.getId()).isPresent())
                .build();
    }

    public SalesRepresentative toEntity(SalesRepresentativeDto salesRepresentativeDto) {

        List<Unavailability> unavailabilityList = new ArrayList<>();
        salesRepresentativeDto.getUnavailabilities().forEach(unavailabilityId -> unavailabilityList.add(unavailabilityRepository.findById(unavailabilityId).orElseThrow(EntityNotFoundException::new)));

        List<Notification> notificationList = new ArrayList<>();
        salesRepresentativeDto.getNotifications().forEach(notificationId -> notificationList.add(notificationRepository.findById(notificationId).orElseThrow(EntityNotFoundException::new)));

        List<Comment> employeeCommentsList = new ArrayList<>();
        salesRepresentativeDto.getEmployeeComments().forEach(commentId -> employeeCommentsList.add(commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new)));

        List<ElementEvent> elementEventList = new ArrayList<>();
        salesRepresentativeDto.getElementEvents().forEach(elementEventId -> elementEventList.add(elementEventRepository.findById(elementEventId).orElseThrow(EntityNotFoundException::new)));

        List<Employment> employmentList = new ArrayList<>();
        salesRepresentativeDto.getEmployments().forEach(employmentId -> employmentList.add(employmentRepository.findById(employmentId).orElseThrow(EntityNotFoundException::new)));

        List<Attachment> attachmentList = new ArrayList<>();
        salesRepresentativeDto.getAttachments().forEach(attachmentId -> attachmentList.add(attachmentRepository.findById(attachmentId).orElseThrow(EntityNotFoundException::new)));

        List<ToolEvent> toolEventList = new ArrayList<>();
        salesRepresentativeDto.getToolEvents().forEach(toolEventId -> toolEventList.add(toolEventRepository.findById(toolEventId).orElseThrow(EntityNotFoundException::new)));


        List<Orders> ordersList = new ArrayList<>();
        salesRepresentativeDto.getOrders().forEach(orderId -> ordersList.add(orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new)));

        SalesRepresentative salesRepresentative = new SalesRepresentative();
        salesRepresentative.setId(salesRepresentativeDto.getId());
        salesRepresentative.setFirstName(salesRepresentativeDto.getFirstName());
        salesRepresentative.setLastName(salesRepresentativeDto.getLastName());
        salesRepresentative.setUsername(salesRepresentativeDto.getUsername());
        salesRepresentative.setRoles(salesRepresentativeDto.getRoles());
        salesRepresentative.setPassword(salesRepresentativeDto.getPassword());
        salesRepresentative.setEmail(salesRepresentativeDto.getEmail());
        salesRepresentative.setPhone(salesRepresentativeDto.getPhone());
        salesRepresentative.setPesel(salesRepresentativeDto.getPesel());
        salesRepresentative.setUnavailabilities(unavailabilityList);
        salesRepresentative.setNotifications(notificationList);
        salesRepresentative.setEmployeeComments(employeeCommentsList);
        salesRepresentative.setElementEvents(elementEventList);
        salesRepresentative.setEmployments(employmentList);
        salesRepresentative.setAttachments(attachmentList);
        salesRepresentative.setToolEvents(toolEventList);
        salesRepresentative.setOrders(ordersList);

        return salesRepresentative;
    }
}
