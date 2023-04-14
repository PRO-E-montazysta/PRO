package com.emontazysta.mapper;

import com.emontazysta.model.Attachment;
import com.emontazysta.model.Comment;
import com.emontazysta.model.ElementEvent;
import com.emontazysta.model.Employment;
import com.emontazysta.model.Fitter;
import com.emontazysta.model.Notification;
import com.emontazysta.model.ToolEvent;
import com.emontazysta.model.OrderStage;
import com.emontazysta.model.Unavailability;
import com.emontazysta.model.dto.FitterDto;
import com.emontazysta.repository.*;
import com.emontazysta.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FitterMapper {

    private final UnavailabilityRepository unavailabilityRepository;
    private final NotificationRepository notificationRepository;
    private final CommentRepository commentRepository;
    private final ElementEventRepository elementEventRepository;
    private final EmploymentRepository employmentRepository;
    private final AttachmentRepository attachmentRepository;
    private final ToolEventRepository toolEventRepository;
    private final OrderStageRepository orderStageRepository;
    private final StatusService statusService;

    public FitterDto toDto(Fitter fitter) {
        return FitterDto.builder()
                .id(fitter.getId())
                .firstName(fitter.getFirstName())
                .lastName(fitter.getLastName())
                .username(fitter.getUsername())
                .roles(fitter.getRoles())
                .email(fitter.getEmail())
                .phone(fitter.getPhone())
                .pesel(fitter.getPesel())
                .unavailabilities(fitter.getUnavailabilities().stream().map(Unavailability::getId).collect(Collectors.toList()))
                .notifications(fitter.getNotifications().stream().map(Notification::getId).collect(Collectors.toList()))
                .employeeComments(fitter.getEmployeeComments().stream().map(Comment::getId).collect(Collectors.toList()))
                .elementEvents(fitter.getElementEvents().stream().map(ElementEvent::getId).collect(Collectors.toList()))
                .employments(fitter.getEmployments().stream().map(Employment::getId).collect(Collectors.toList()))
                .attachments(fitter.getAttachments().stream().map(Attachment::getId).collect(Collectors.toList()))
                .toolEvents(fitter.getToolEvents().stream().map(ToolEvent::getId).collect(Collectors.toList()))
                .workingOn(fitter.getWorkingOn().stream().map(OrderStage::getId).collect(Collectors.toList()))
                .status(statusService.checkUnavailability(fitter) == null ? "AVAILABLE" : String.valueOf(statusService.checkUnavailability(fitter).getTypeOfUnavailability()))
                .unavailableFrom(statusService.checkUnavailability(fitter) == null ? null : statusService.checkUnavailability(fitter).getUnavailableFrom())
                .unavailableTo(statusService.checkUnavailability(fitter) == null ? null : statusService.checkUnavailability(fitter).getUnavailableTo())
                .unavailbilityDescription(statusService.checkUnavailability(fitter) == null ? null : statusService.checkUnavailability(fitter).getDescription())
                .build();
    }

    public Fitter toEntity(FitterDto fitterDto) {

        List<Unavailability> unavailabilityList = new ArrayList<>();
        fitterDto.getUnavailabilities().forEach(unavailabilityId -> unavailabilityList.add(unavailabilityRepository.getReferenceById(unavailabilityId)));

        List<Notification> notificationList = new ArrayList<>();
        fitterDto.getNotifications().forEach(notificationId -> notificationList.add(notificationRepository.getReferenceById(notificationId)));

        List<Comment> employeeCommentsList = new ArrayList<>();
        fitterDto.getEmployeeComments().forEach(commentId -> employeeCommentsList.add(commentRepository.getReferenceById(commentId)));

        List<ElementEvent> elementEventList = new ArrayList<>();
        fitterDto.getElementEvents().forEach(elementEventId -> elementEventList.add(elementEventRepository.getReferenceById(elementEventId)));

        List<Employment> employmentList = new ArrayList<>();
        fitterDto.getEmployments().forEach(employmentId -> employmentList.add(employmentRepository.getReferenceById(employmentId)));

        List<Attachment> attachmentList = new ArrayList<>();
        fitterDto.getAttachments().forEach(attachmentId -> attachmentList.add(attachmentRepository.getReferenceById(attachmentId)));

        List<ToolEvent> toolEventList = new ArrayList<>();
        fitterDto.getToolEvents().forEach(toolEventId -> toolEventList.add(toolEventRepository.getReferenceById(toolEventId)));

        List<OrderStage> workingOnList = new ArrayList<>();
        fitterDto.getWorkingOn().forEach(workingOnId -> workingOnList.add(orderStageRepository.getReferenceById(workingOnId)));

        Fitter fitter = new Fitter();
        fitter.setId(fitterDto.getId());
        fitter.setFirstName(fitterDto.getFirstName());
        fitter.setLastName(fitterDto.getLastName());
        fitter.setUsername(fitterDto.getUsername());
        fitter.setRoles(fitterDto.getRoles());
        fitter.setPassword(fitterDto.getPassword());
        fitter.setEmail(fitterDto.getEmail());
        fitter.setPhone(fitterDto.getPhone());
        fitter.setPesel(fitterDto.getPesel());
        fitter.setUnavailabilities(unavailabilityList);
        fitter.setNotifications(notificationList);
        fitter.setEmployeeComments(employeeCommentsList);
        fitter.setElementEvents(elementEventList);
        fitter.setEmployments(employmentList);
        fitter.setAttachments(attachmentList);
        fitter.setToolEvents(toolEventList);
        fitter.setWorkingOn(workingOnList);

        return fitter;
    }
}
