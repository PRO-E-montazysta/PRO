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
                .email(warehouseManager.getEmail())
                .phone(warehouseManager.getPhone())
                .pesel(warehouseManager.getPesel())
                .unavailabilities(warehouseManager.getUnavailabilities().stream().map(Unavailability::getId).collect(Collectors.toList()))
                .notifications(warehouseManager.getNotifications().stream().map(Notification::getId).collect(Collectors.toList()))
                .employeeComments(warehouseManager.getEmployeeComments().stream().map(Comment::getId).collect(Collectors.toList()))
                .elementEvents(warehouseManager.getElementEvents().stream().map(ElementEvent::getId).collect(Collectors.toList()))
                .employments(warehouseManager.getEmployments().stream().map(Employment::getId).collect(Collectors.toList()))
                .attachments(warehouseManager.getAttachments().stream().map(Attachment::getId).collect(Collectors.toList()))
                .toolEvents(warehouseManager.getToolEvents().stream().map(ToolEvent::getId).collect(Collectors.toList()))
                .releaseTools(warehouseManager.getReleasedTools().stream().map(ToolRelease::getId).collect(Collectors.toList()))
                .elementReturnReleases(warehouseManager.getElementReturnReleases().stream().map(ElementReturnRelease::getId).collect(Collectors.toList()))
                .demandAdHocs(warehouseManager.getDemandAdHocs().stream().map(DemandAdHoc::getId).collect(Collectors.toList()))
                .build();
    }

    public WarehouseManager toEntity(WarehouseManagerDto warehouseManagerDto) {

        List<Unavailability> unavailabilityList = new ArrayList<>();
        warehouseManagerDto.getUnavailabilities().forEach(unavailabilityId -> unavailabilityList.add(unavailabilityRepository.getReferenceById(unavailabilityId)));

        List<Notification> notificationList = new ArrayList<>();
        warehouseManagerDto.getNotifications().forEach(notificationId -> notificationList.add(notificationRepository.getReferenceById(notificationId)));

        List<Comment> employeeCommentsList = new ArrayList<>();
        warehouseManagerDto.getEmployeeComments().forEach(commentId -> employeeCommentsList.add(commentRepository.getReferenceById(commentId)));

        List<ElementEvent> elementEventList = new ArrayList<>();
        warehouseManagerDto.getElementEvents().forEach(elementEventId -> elementEventList.add(elementEventRepository.getReferenceById(elementEventId)));

        List<Employment> employmentList = new ArrayList<>();
        warehouseManagerDto.getEmployments().forEach(employmentId -> employmentList.add(employmentRepository.getReferenceById(employmentId)));

        List<Attachment> attachmentList = new ArrayList<>();
        warehouseManagerDto.getAttachments().forEach(attachmentId -> attachmentList.add(attachmentRepository.getReferenceById(attachmentId)));

        List<ToolEvent> toolEventList = new ArrayList<>();
        warehouseManagerDto.getToolEvents().forEach(toolEventId -> toolEventList.add(toolEventRepository.getReferenceById(toolEventId)));

        List<ToolRelease> toolReleaseList = new ArrayList<>();
        warehouseManagerDto.getReleaseTools().forEach(toolReleaseId -> toolReleaseList.add(toolReleaseRepository.getReferenceById(toolReleaseId)));

        List<ElementReturnRelease> elementReturnReleaseList = new ArrayList<>();
        warehouseManagerDto.getElementReturnReleases().forEach(elementReturnReleaseId -> elementReturnReleaseList.add(elementReturnReleaseRepository.getReferenceById(elementReturnReleaseId)));

        List<DemandAdHoc> demandAdHocList = new ArrayList<>();
        warehouseManagerDto.getDemandAdHocs().forEach(demandAdHocId -> demandAdHocList.add(demandAdHocRepository.getReferenceById(demandAdHocId)));

        WarehouseManager warehouseManager = new WarehouseManager();
        warehouseManager.setId(warehouseManagerDto.getId());
        warehouseManager.setFirstName(warehouseManagerDto.getFirstName());
        warehouseManager.setLastName(warehouseManagerDto.getLastName());
        warehouseManager.setUsername(warehouseManagerDto.getUsername());
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

        return warehouseManager;
    }
}
