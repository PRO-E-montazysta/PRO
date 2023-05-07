package com.emontazysta.mapper;

import com.emontazysta.model.Attachment;
import com.emontazysta.model.Comment;
import com.emontazysta.model.DemandAdHoc;
import com.emontazysta.model.ElementEvent;
import com.emontazysta.model.ElementReturnRelease;
import com.emontazysta.model.Employment;
import com.emontazysta.model.Notification;
import com.emontazysta.model.ToolEvent;
import com.emontazysta.model.ToolRelease;
import com.emontazysta.model.Unavailability;
import com.emontazysta.model.Warehouseman;
import com.emontazysta.model.dto.WarehousemanDto;
import com.emontazysta.repository.AttachmentRepository;
import com.emontazysta.repository.CommentRepository;
import com.emontazysta.repository.DemandAdHocRepository;
import com.emontazysta.repository.ElementEventRepository;
import com.emontazysta.repository.ElementReturnReleaseRepository;
import com.emontazysta.repository.EmploymentRepository;
import com.emontazysta.repository.NotificationRepository;
import com.emontazysta.repository.ToolEventRepository;
import com.emontazysta.repository.ToolReleaseRepository;
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
public class WarehousemanMapper {

    private final UnavailabilityRepository unavailabilityRepository;
    private final NotificationRepository notificationRepository;
    private final CommentRepository commentRepository;
    private final ElementEventRepository elementEventRepository;
    private final EmploymentRepository employmentRepository;
    private final AttachmentRepository attachmentRepository;
    private final ToolEventRepository toolEventRepository;
    private final ToolReleaseRepository toolReleaseRepository;
    private final ElementReturnReleaseRepository elementReturnReleaseRepository;
    private final DemandAdHocRepository demandAdHocRepository;
    private final StatusService statusService;

    public WarehousemanDto toDto(Warehouseman warehouseman) {

        return WarehousemanDto.builder()
                .id(warehouseman.getId())
                .firstName(warehouseman.getFirstName())
                .lastName(warehouseman.getLastName())
                .username(warehouseman.getUsername())
                .roles(warehouseman.getRoles())
                .email(warehouseman.getEmail())
                .phone(warehouseman.getPhone())
                .pesel(warehouseman.getPesel())
                .unavailabilities(warehouseman.getUnavailabilities().stream()
                        .map(Unavailability::getId)
                        .collect(Collectors.toList()))
                .notifications(warehouseman.getNotifications().stream()
                        .map(Notification::getId)
                        .collect(Collectors.toList()))
                .employeeComments(warehouseman.getEmployeeComments().stream()
                        .map(Comment::getId)
                        .collect(Collectors.toList()))
                .elementEvents(warehouseman.getElementEvents().stream()
                        .map(ElementEvent::getId)
                        .collect(Collectors.toList()))
                .employments(warehouseman.getEmployments().stream()
                        .map(Employment::getId)
                        .collect(Collectors.toList()))
                .attachments(warehouseman.getAttachments().stream()
                        .map(Attachment::getId)
                        .collect(Collectors.toList()))
                .toolEvents(warehouseman.getToolEvents().stream()
                        .map(ToolEvent::getId)
                        .collect(Collectors.toList()))
                .releaseTools(warehouseman.getReleasedTools().stream()
                        .map(ToolRelease::getId)
                        .collect(Collectors.toList()))
                .elementReturnReleases(warehouseman.getElementReturnReleases().stream()
                        .map(ElementReturnRelease::getId)
                        .collect(Collectors.toList()))
                .status(statusService.checkUnavailability(warehouseman) == null ? "AVAILABLE" : String.valueOf(statusService.checkUnavailability(warehouseman).getTypeOfUnavailability()))
                .unavailableFrom(statusService.checkUnavailability(warehouseman) == null ? null : statusService.checkUnavailability(warehouseman).getUnavailableFrom())
                .unavailableTo(statusService.checkUnavailability(warehouseman) == null ? null : statusService.checkUnavailability(warehouseman).getUnavailableTo())
                .unavailbilityDescription(statusService.checkUnavailability(warehouseman) == null ? null : statusService.checkUnavailability(warehouseman).getDescription())
                .deleted(warehouseman.isDeleted())
                .build();
    }

    public Warehouseman toEntity(WarehousemanDto warehousemanDto) {

        List<Unavailability> unavailabilityList = new ArrayList<>();
        warehousemanDto.getUnavailabilities().forEach(unavailabilityId -> unavailabilityList.add(unavailabilityRepository.findById(unavailabilityId).orElseThrow(EntityNotFoundException::new)));

        List<Notification> notificationList = new ArrayList<>();
        warehousemanDto.getNotifications().forEach(notificationId -> notificationList.add(notificationRepository.findById(notificationId).orElseThrow(EntityNotFoundException::new)));

        List<Comment> employeeCommentsList = new ArrayList<>();
        warehousemanDto.getEmployeeComments().forEach(commentId -> employeeCommentsList.add(commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new)));

        List<ElementEvent> elementEventList = new ArrayList<>();
        warehousemanDto.getElementEvents().forEach(elementEventId -> elementEventList.add(elementEventRepository.findById(elementEventId).orElseThrow(EntityNotFoundException::new)));

        List<Employment> employmentList = new ArrayList<>();
        warehousemanDto.getEmployments().forEach(employmentId -> employmentList.add(employmentRepository.findById(employmentId).orElseThrow(EntityNotFoundException::new)));

        List<Attachment> attachmentList = new ArrayList<>();
        warehousemanDto.getAttachments().forEach(attachmentId -> attachmentList.add(attachmentRepository.findById(attachmentId).orElseThrow(EntityNotFoundException::new)));

        List<ToolEvent> toolEventList = new ArrayList<>();
        warehousemanDto.getToolEvents().forEach(toolEventId -> toolEventList.add(toolEventRepository.findById(toolEventId).orElseThrow(EntityNotFoundException::new)));

        List<ToolRelease> toolReleaseList = new ArrayList<>();
        warehousemanDto.getReleaseTools().forEach(toolReleaseId -> toolReleaseList.add(toolReleaseRepository.findById(toolReleaseId).orElseThrow(EntityNotFoundException::new)));

        List<ElementReturnRelease> elementReturnReleaseList = new ArrayList<>();
        warehousemanDto.getElementReturnReleases().forEach(elementReturnReleaseId -> elementReturnReleaseList.add(elementReturnReleaseRepository.findById(elementReturnReleaseId).orElseThrow(EntityNotFoundException::new)));

        Warehouseman warehouseman = new Warehouseman();
        warehouseman.setId(warehousemanDto.getId());
        warehouseman.setFirstName(warehousemanDto.getFirstName());
        warehouseman.setLastName(warehousemanDto.getLastName());
        warehouseman.setUsername(warehousemanDto.getUsername());
        warehouseman.setRoles(warehousemanDto.getRoles());
        warehouseman.setPassword(warehousemanDto.getPassword());
        warehouseman.setEmail(warehousemanDto.getEmail());
        warehouseman.setPhone(warehousemanDto.getPhone());
        warehouseman.setPesel(warehousemanDto.getPesel());
        warehouseman.setUnavailabilities(unavailabilityList);
        warehouseman.setNotifications(notificationList);
        warehouseman.setEmployeeComments(employeeCommentsList);
        warehouseman.setElementEvents(elementEventList);
        warehouseman.setEmployments(employmentList);
        warehouseman.setAttachments(attachmentList);
        warehouseman.setToolEvents(toolEventList);
        warehouseman.setReleasedTools(toolReleaseList);
        warehouseman.setElementReturnReleases(elementReturnReleaseList);

        return warehouseman;
    }
}
