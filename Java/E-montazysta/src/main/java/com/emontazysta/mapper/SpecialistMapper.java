package com.emontazysta.mapper;

import com.emontazysta.model.Attachment;
import com.emontazysta.model.Comment;
import com.emontazysta.model.DemandAdHoc;
import com.emontazysta.model.ElementEvent;
import com.emontazysta.model.Employment;
import com.emontazysta.model.Notification;
import com.emontazysta.model.Orders;
import com.emontazysta.model.Specialist;
import com.emontazysta.model.ToolEvent;
import com.emontazysta.model.Unavailability;
import com.emontazysta.model.dto.SpecialistDto;
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
public class SpecialistMapper {

    private final UnavailabilityRepository unavailabilityRepository;
    private final NotificationRepository notificationRepository;
    private final CommentRepository commentRepository;
    private final ElementEventRepository elementEventRepository;
    private final EmploymentRepository employmentRepository;
    private final AttachmentRepository attachmentRepository;
    private final ToolEventRepository toolEventRepository;
    private final OrderRepository orderRepository;
    private final DemandAdHocRepository demandAdHocRepository;

    public SpecialistDto toDto(Specialist specialist) {

        return SpecialistDto.builder()
                .id(specialist.getId())
                .firstName(specialist.getFirstName())
                .lastName(specialist.getLastName())
                .username(specialist.getUsername())
                .email(specialist.getEmail())
                .phone(specialist.getPhone())
                .pesel(specialist.getPesel())
                .unavailabilities(specialist.getUnavailabilities().stream()
                        .filter(unavailability -> !unavailability.isDeleted())
                        .map(Unavailability::getId)
                        .collect(Collectors.toList()))
                .notifications(specialist.getNotifications().stream()
                        .filter(notification -> !notification.isDeleted())
                        .map(Notification::getId)
                        .collect(Collectors.toList()))
                .employeeComments(specialist.getEmployeeComments().stream()
                        .filter(comment -> !comment.isDeleted())
                        .map(Comment::getId)
                        .collect(Collectors.toList()))
                .elementEvents(specialist.getElementEvents().stream()
                        .filter(elementEvent -> !elementEvent.isDeleted())
                        .map(ElementEvent::getId)
                        .collect(Collectors.toList()))
                .employments(specialist.getEmployments().stream()
                        .filter(employment -> !employment.isDeleted())
                        .map(Employment::getId)
                        .collect(Collectors.toList()))
                .attachments(specialist.getAttachments().stream()
                        .filter(attachment -> !attachment.isDeleted())
                        .map(Attachment::getId)
                        .collect(Collectors.toList()))
                .toolEvents(specialist.getToolEvents().stream()
                        .filter(toolEvent -> !toolEvent.isDeleted())
                        .map(ToolEvent::getId)
                        .collect(Collectors.toList()))
                .orders(specialist.getOrders().stream()
                        .filter(order -> !order.isDeleted())
                        .map(Orders::getId)
                        .collect(Collectors.toList()))
                .demandAdHocs(specialist.getDemandAdHocs().stream()
                        .filter(demandAdHoc -> !demandAdHoc.isDeleted())
                        .map(DemandAdHoc::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    public Specialist toEntity(SpecialistDto specialistDto) {

        List<Unavailability> unavailabilityList = new ArrayList<>();
        specialistDto.getUnavailabilities().forEach(unavailabilityId -> unavailabilityList.add(unavailabilityRepository.findById(unavailabilityId).orElseThrow(EntityNotFoundException::new)));

        List<Notification> notificationList = new ArrayList<>();
        specialistDto.getNotifications().forEach(notificationId -> notificationList.add(notificationRepository.findById(notificationId).orElseThrow(EntityNotFoundException::new)));

        List<Comment> employeeCommentsList = new ArrayList<>();
        specialistDto.getEmployeeComments().forEach(commentId -> employeeCommentsList.add(commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new)));

        List<ElementEvent> elementEventList = new ArrayList<>();
        specialistDto.getElementEvents().forEach(elementEventId -> elementEventList.add(elementEventRepository.findById(elementEventId).orElseThrow(EntityNotFoundException::new)));

        List<Employment> employmentList = new ArrayList<>();
        specialistDto.getEmployments().forEach(employmentId -> employmentList.add(employmentRepository.findById(employmentId).orElseThrow(EntityNotFoundException::new)));

        List<Attachment> attachmentList = new ArrayList<>();
        specialistDto.getAttachments().forEach(attachmentId -> attachmentList.add(attachmentRepository.findById(attachmentId).orElseThrow(EntityNotFoundException::new)));

        List<ToolEvent> toolEventList = new ArrayList<>();
        specialistDto.getToolEvents().forEach(toolEventId -> toolEventList.add(toolEventRepository.findById(toolEventId).orElseThrow(EntityNotFoundException::new)));

        List<Orders> ordersList = new ArrayList<>();
        specialistDto.getOrders().forEach(orderId -> ordersList.add(orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new)));

        List<DemandAdHoc> demandAdHocList = new ArrayList<>();
        specialistDto.getDemandAdHocs().forEach(demandAdHocId -> demandAdHocList.add(demandAdHocRepository.findById(demandAdHocId).orElseThrow(EntityNotFoundException::new)));

        Specialist specialist = new Specialist();
        specialist.setId(specialistDto.getId());
        specialist.setFirstName(specialistDto.getFirstName());
        specialist.setLastName(specialistDto.getLastName());
        specialist.setUsername(specialistDto.getUsername());
        specialist.setPassword(specialistDto.getPassword());
        specialist.setEmail(specialistDto.getEmail());
        specialist.setPhone(specialistDto.getPhone());
        specialist.setPesel(specialistDto.getPesel());
        specialist.setUnavailabilities(unavailabilityList);
        specialist.setNotifications(notificationList);
        specialist.setEmployeeComments(employeeCommentsList);
        specialist.setElementEvents(elementEventList);
        specialist.setEmployments(employmentList);
        specialist.setAttachments(attachmentList);
        specialist.setToolEvents(toolEventList);
        specialist.setOrders(ordersList);
        specialist.setDemandAdHocs(demandAdHocList);

        return specialist;
    }
}
