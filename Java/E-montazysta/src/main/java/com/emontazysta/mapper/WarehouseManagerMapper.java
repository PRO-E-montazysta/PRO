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
import com.emontazysta.model.WarehouseManager;
import com.emontazysta.model.dto.WarehouseManagerDto;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WarehouseManagerMapper {

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

    public WarehouseManagerDto toDto(WarehouseManager warehouseManager) {

        return WarehouseManagerDto.builder()
                .id(warehouseManager.getId())
                .firstName(warehouseManager.getFirstName())
                .lastName(warehouseManager.getLastName())
                .username(warehouseManager.getUsername())
                .roles(warehouseManager.getRoles())
                .email(warehouseManager.getEmail())
                .phone(warehouseManager.getPhone())
                .pesel(warehouseManager.getPesel())
                .unavailabilities(warehouseManager.getUnavailabilities().stream()
                        .filter(unavailability -> !unavailability.isDeleted())
                        .map(Unavailability::getId)
                        .collect(Collectors.toList()))
                .notifications(warehouseManager.getNotifications().stream()
                        .filter(notification -> !notification.isDeleted())
                        .map(Notification::getId)
                        .collect(Collectors.toList()))
                .employeeComments(warehouseManager.getEmployeeComments().stream()
                        .filter(comment -> !comment.isDeleted())
                        .map(Comment::getId)
                        .collect(Collectors.toList()))
                .elementEvents(warehouseManager.getElementEvents().stream()
                        .filter(elementEvent -> !elementEvent.isDeleted())
                        .map(ElementEvent::getId)
                        .collect(Collectors.toList()))
                .employments(warehouseManager.getEmployments().stream()
                        .filter(employment -> !employment.isDeleted())
                        .map(Employment::getId)
                        .collect(Collectors.toList()))
                .attachments(warehouseManager.getAttachments().stream()
                        .filter(attachment -> !attachment.isDeleted())
                        .map(Attachment::getId)
                        .collect(Collectors.toList()))
                .toolEvents(warehouseManager.getToolEvents().stream()
                        .filter(toolEvent -> !toolEvent.isDeleted())
                        .map(ToolEvent::getId)
                        .collect(Collectors.toList()))
                .releaseTools(warehouseManager.getReleasedTools().stream()
                        .filter(toolRelease -> !toolRelease.isDeleted())
                        .map(ToolRelease::getId)
                        .collect(Collectors.toList()))
                .elementReturnReleases(warehouseManager.getElementReturnReleases().stream()
                        .filter(elementReturnRelease -> !elementReturnRelease.isDeleted())
                        .map(ElementReturnRelease::getId)
                        .collect(Collectors.toList()))
                .demandAdHocs(warehouseManager.getDemandAdHocs().stream()
                        .filter(demandAdHoc -> !demandAdHoc.isDeleted())
                        .map(DemandAdHoc::getId)
                        .collect(Collectors.toList()))
                .acceptedDemandAdHocs(warehouseManager.getAcceptedDemandAdHocs().stream()
                        .filter(demandAdHoc -> !demandAdHoc.isDeleted())
                        .map(DemandAdHoc::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    public WarehouseManager toEntity(WarehouseManagerDto warehouseManagerDto) {

        List<Unavailability> unavailabilityList = new ArrayList<>();
        warehouseManagerDto.getUnavailabilities().forEach(unavailabilityId -> unavailabilityList.add(unavailabilityRepository.findById(unavailabilityId).orElseThrow(EntityNotFoundException::new)));

        List<Notification> notificationList = new ArrayList<>();
        warehouseManagerDto.getNotifications().forEach(notificationId -> notificationList.add(notificationRepository.findById(notificationId).orElseThrow(EntityNotFoundException::new)));

        List<Comment> employeeCommentsList = new ArrayList<>();
        warehouseManagerDto.getEmployeeComments().forEach(commentId -> employeeCommentsList.add(commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new)));

        List<ElementEvent> elementEventList = new ArrayList<>();
        warehouseManagerDto.getElementEvents().forEach(elementEventId -> elementEventList.add(elementEventRepository.findById(elementEventId).orElseThrow(EntityNotFoundException::new)));

        List<Employment> employmentList = new ArrayList<>();
        warehouseManagerDto.getEmployments().forEach(employmentId -> employmentList.add(employmentRepository.findById(employmentId).orElseThrow(EntityNotFoundException::new)));

        List<Attachment> attachmentList = new ArrayList<>();
        warehouseManagerDto.getAttachments().forEach(attachmentId -> attachmentList.add(attachmentRepository.findById(attachmentId).orElseThrow(EntityNotFoundException::new)));

        List<ToolEvent> toolEventList = new ArrayList<>();
        warehouseManagerDto.getToolEvents().forEach(toolEventId -> toolEventList.add(toolEventRepository.findById(toolEventId).orElseThrow(EntityNotFoundException::new)));

        List<ToolRelease> toolReleaseList = new ArrayList<>();
        warehouseManagerDto.getReleaseTools().forEach(toolReleaseId -> toolReleaseList.add(toolReleaseRepository.findById(toolReleaseId).orElseThrow(EntityNotFoundException::new)));

        List<ElementReturnRelease> elementReturnReleaseList = new ArrayList<>();
        warehouseManagerDto.getElementReturnReleases().forEach(elementReturnReleaseId -> elementReturnReleaseList.add(elementReturnReleaseRepository.findById(elementReturnReleaseId).orElseThrow(EntityNotFoundException::new)));

        List<DemandAdHoc> demandAdHocList = new ArrayList<>();
        warehouseManagerDto.getDemandAdHocs().forEach(demandAdHocId -> demandAdHocList.add(demandAdHocRepository.findById(demandAdHocId).orElseThrow(EntityNotFoundException::new)));

        List<DemandAdHoc> acceptedDemandAdHocList = new ArrayList<>();
        warehouseManagerDto.getAcceptedDemandAdHocs().forEach(acceptedDemandAdHocId -> acceptedDemandAdHocList.add(demandAdHocRepository.findById(acceptedDemandAdHocId).orElseThrow(EntityNotFoundException::new)));

        WarehouseManager warehouseManager = new WarehouseManager();
        warehouseManager.setId(warehouseManagerDto.getId());
        warehouseManager.setFirstName(warehouseManagerDto.getFirstName());
        warehouseManager.setLastName(warehouseManagerDto.getLastName());
        warehouseManager.setUsername(warehouseManagerDto.getUsername());
        warehouseManager.setRoles(warehouseManagerDto.getRoles());
        warehouseManager.setPassword(warehouseManagerDto.getPassword());
        warehouseManager.setEmail(warehouseManagerDto.getEmail());
        warehouseManager.setPhone(warehouseManagerDto.getPhone());
        warehouseManager.setPesel(warehouseManagerDto.getPesel());
        warehouseManager.setUnavailabilities(unavailabilityList);
        warehouseManager.setNotifications(notificationList);
        warehouseManager.setEmployeeComments(employeeCommentsList);
        warehouseManager.setElementEvents(elementEventList);
        warehouseManager.setEmployments(employmentList);
        warehouseManager.setAttachments(attachmentList);
        warehouseManager.setToolEvents(toolEventList);
        warehouseManager.setReleasedTools(toolReleaseList);
        warehouseManager.setElementReturnReleases(elementReturnReleaseList);
        warehouseManager.setDemandAdHocs(demandAdHocList);
        warehouseManager.setAcceptedDemandAdHocs(acceptedDemandAdHocList);

        return warehouseManager;
    }
}
