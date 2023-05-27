package com.emontazysta.mapper;

import com.emontazysta.model.Attachment;
import com.emontazysta.model.Comment;
import com.emontazysta.model.DemandAdHoc;
import com.emontazysta.model.ElementEvent;
import com.emontazysta.model.Employment;
import com.emontazysta.model.Foreman;
import com.emontazysta.model.Notification;
import com.emontazysta.model.OrderStage;
import com.emontazysta.model.Orders;
import com.emontazysta.model.ToolEvent;
import com.emontazysta.model.Unavailability;
import com.emontazysta.model.dto.ForemanDto;
import com.emontazysta.repository.AttachmentRepository;
import com.emontazysta.repository.CommentRepository;
import com.emontazysta.repository.DemandAdHocRepository;
import com.emontazysta.repository.ElementEventRepository;
import com.emontazysta.repository.EmploymentRepository;
import com.emontazysta.repository.NotificationRepository;
import com.emontazysta.repository.OrderRepository;
import com.emontazysta.repository.OrderStageRepository;
import com.emontazysta.repository.ToolEventRepository;
import com.emontazysta.repository.UnavailabilityRepository;
import com.emontazysta.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ForemanMapper {

    private final UnavailabilityRepository unavailabilityRepository;
    private final NotificationRepository notificationRepository;
    private final CommentRepository commentRepository;
    private final ElementEventRepository elementEventRepository;
    private final EmploymentRepository employmentRepository;
    private final AttachmentRepository attachmentRepository;
    private final ToolEventRepository toolEventRepository;
    private final OrderStageRepository orderStageRepository;
    private final OrderRepository orderRepository;
    private final DemandAdHocRepository demandAdHocRepository;
    private final StatusService statusService;

    public ForemanDto toDto(Foreman foreman) {
        return ForemanDto.builder()
                .id(foreman.getId())
                .firstName(foreman.getFirstName())
                .lastName(foreman.getLastName())
                .username(foreman.getUsername())
                .roles(foreman.getRoles())
                .email(foreman.getEmail())
                .phone(foreman.getPhone())
                .pesel(foreman.getPesel())
                .unavailabilities(foreman.getUnavailabilities().stream().map(Unavailability::getId).collect(Collectors.toList()))
                .notifications(foreman.getNotifications().stream().map(Notification::getId).collect(Collectors.toList()))
                .employeeComments(foreman.getEmployeeComments().stream().map(Comment::getId).collect(Collectors.toList()))
                .elementEvents(foreman.getElementEvents().stream().map(ElementEvent::getId).collect(Collectors.toList()))
                .employments(foreman.getEmployments().stream().map(Employment::getId).collect(Collectors.toList()))
                .attachments(foreman.getAttachments().stream().map(Attachment::getId).collect(Collectors.toList()))
                .toolEvents(foreman.getToolEvents().stream().map(ToolEvent::getId).collect(Collectors.toList()))
                .workingOn(foreman.getWorkingOn().stream().map(OrderStage::getId).collect(Collectors.toList()))
                .assignedOrders(foreman.getAssignedOrders().stream().map(Orders::getId).collect(Collectors.toList()))
                .demandsAdHocs(foreman.getDemandsAdHocs().stream().map(DemandAdHoc::getId).collect(Collectors.toList()))
                .status(statusService.checkUnavailability(foreman) == null ? "AVAILABLE" : String.valueOf(statusService.checkUnavailability(foreman).getTypeOfUnavailability()))
                .unavailableFrom(statusService.checkUnavailability(foreman) == null ? null : statusService.checkUnavailability(foreman).getUnavailableFrom())
                .unavailableTo(statusService.checkUnavailability(foreman) == null ? null : statusService.checkUnavailability(foreman).getUnavailableTo())
                .unavailbilityDescription(statusService.checkUnavailability(foreman) == null ? null : statusService.checkUnavailability(foreman).getDescription())
                .deleted(foreman.isDeleted())
                .build();
    }

    public Foreman toEntity(ForemanDto foremanDto) {

        List<Unavailability> unavailabilityList = new ArrayList<>();
        foremanDto.getUnavailabilities().forEach(unavailabilityId -> unavailabilityList.add(unavailabilityRepository.findById(unavailabilityId).orElseThrow(EntityNotFoundException::new)));

        List<Notification> notificationList = new ArrayList<>();
        foremanDto.getNotifications().forEach(notificationId -> notificationList.add(notificationRepository.findById(notificationId).orElseThrow(EntityNotFoundException::new)));

        List<Comment> employeeCommentsList = new ArrayList<>();
        foremanDto.getEmployeeComments().forEach(commentId -> employeeCommentsList.add(commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new)));

        List<ElementEvent> elementEventList = new ArrayList<>();
        foremanDto.getElementEvents().forEach(elementEventId -> elementEventList.add(elementEventRepository.findById(elementEventId).orElseThrow(EntityNotFoundException::new)));

        List<Employment> employmentList = new ArrayList<>();
        foremanDto.getEmployments().forEach(employmentId -> employmentList.add(employmentRepository.findById(employmentId).orElseThrow(EntityNotFoundException::new)));

        List<Attachment> attachmentList = new ArrayList<>();
        foremanDto.getAttachments().forEach(attachmentId -> attachmentList.add(attachmentRepository.findById(attachmentId).orElseThrow(EntityNotFoundException::new)));

        List<ToolEvent> toolEventList = new ArrayList<>();
        foremanDto.getToolEvents().forEach(toolEventId -> toolEventList.add(toolEventRepository.findById(toolEventId).orElseThrow(EntityNotFoundException::new)));

        List<OrderStage> workingOnList = new ArrayList<>();
        foremanDto.getWorkingOn().forEach(workingOnId -> workingOnList.add(orderStageRepository.findById(workingOnId).orElseThrow(EntityNotFoundException::new)));

        List<Orders> ordersList = new ArrayList<>();
        foremanDto.getAssignedOrders().forEach(orderId -> ordersList.add(orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new)));

        List<DemandAdHoc> demandAdHocsList = new ArrayList<>();
        foremanDto.getDemandsAdHocs().forEach(demandAdHocId -> demandAdHocsList.add(demandAdHocRepository.findById(demandAdHocId).orElseThrow(EntityNotFoundException::new)));

        Foreman foreman = new Foreman();
        foreman.setId(foremanDto.getId());
        foreman.setFirstName(foremanDto.getFirstName());
        foreman.setLastName(foremanDto.getLastName());
        foreman.setUsername(foremanDto.getUsername());
        foreman.setRoles(foremanDto.getRoles());
        foreman.setPassword(foremanDto.getPassword());
        foreman.setEmail(foremanDto.getEmail());
        foreman.setPhone(foremanDto.getPhone());
        foreman.setPesel(foremanDto.getPesel());
        foreman.setUnavailabilities(unavailabilityList);
        foreman.setNotifications(notificationList);
        foreman.setEmployeeComments(employeeCommentsList);
        foreman.setElementEvents(elementEventList);
        foreman.setEmployments(employmentList);
        foreman.setAttachments(attachmentList);
        foreman.setToolEvents(toolEventList);
        foreman.setWorkingOn(workingOnList);
        foreman.setAssignedOrders(ordersList);
        foreman.setDemandsAdHocs(demandAdHocsList);

        return foreman;
    }
}
