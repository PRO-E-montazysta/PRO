package com.emontazysta.mapper;

import com.emontazysta.model.*;
import com.emontazysta.model.dto.CompanyAdminDto;
import com.emontazysta.model.CompanyAdmin;
import com.emontazysta.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompanyAdminMapper {

    private final UnavailabilityRepository unavailabilityRepository;
    private final NotificationRepository notificationRepository;
    private final CommentRepository commentRepository;
    private final ElementEventRepository elementEventRepository;
    private final EmploymentRepository employmentRepository;
    private final AttachmentRepository attachmentRepository;
    private final ToolEventRepository toolEventRepository;
    private final OrderRepository orderRepository;

    public CompanyAdminDto toDto(CompanyAdmin companyAdmin) {

        return CompanyAdminDto.builder()
                .id(companyAdmin.getId())
                .firstName(companyAdmin.getFirstName())
                .lastName(companyAdmin.getLastName())
                .username(companyAdmin.getUsername())
                .email(companyAdmin.getEmail())
                .phone(companyAdmin.getPhone())
                .pesel(companyAdmin.getPesel())
                .unavailabilities(companyAdmin.getUnavailabilities().stream()
                        .filter(unavailability -> !unavailability.isDeleted())
                        .map(Unavailability::getId)
                        .collect(Collectors.toList()))
                .notifications(companyAdmin.getNotifications().stream()
                        .filter(notification -> !notification.isDeleted())
                        .map(Notification::getId)
                        .collect(Collectors.toList()))
                .employeeComments(companyAdmin.getEmployeeComments().stream()
                        .filter(comment -> !comment.isDeleted())
                        .map(Comment::getId)
                        .collect(Collectors.toList()))
                .elementEvents(companyAdmin.getElementEvents().stream()
                        .filter(elementEvent -> !elementEvent.isDeleted())
                        .map(ElementEvent::getId)
                        .collect(Collectors.toList()))
                .employments(companyAdmin.getEmployments().stream()
                        .filter(employment -> !employment.isDeleted())
                        .map(Employment::getId)
                        .collect(Collectors.toList()))
                .attachments(companyAdmin.getAttachments().stream()
                        .filter(attachment -> !attachment.isDeleted())
                        .map(Attachment::getId)
                        .collect(Collectors.toList()))
                .toolEvents(companyAdmin.getToolEvents().stream()
                        .filter(toolEvent -> !toolEvent.isDeleted())
                        .map(ToolEvent::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    public CompanyAdmin toEntity(CompanyAdminDto companyAdminDto) {

        List<Unavailability> unavailabilityList = new ArrayList<>();
        companyAdminDto.getUnavailabilities().forEach(unavailabilityId -> unavailabilityList.add(unavailabilityRepository.findById(unavailabilityId).orElseThrow(EntityNotFoundException::new)));

        List<Notification> notificationList = new ArrayList<>();
        companyAdminDto.getNotifications().forEach(notificationId -> notificationList.add(notificationRepository.findById(notificationId).orElseThrow(EntityNotFoundException::new)));

        List<Comment> employeeCommentsList = new ArrayList<>();
        companyAdminDto.getEmployeeComments().forEach(commentId -> employeeCommentsList.add(commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new)));

        List<ElementEvent> elementEventList = new ArrayList<>();
        companyAdminDto.getElementEvents().forEach(elementEventId -> elementEventList.add(elementEventRepository.findById(elementEventId).orElseThrow(EntityNotFoundException::new)));

        List<Employment> employmentList = new ArrayList<>();
        companyAdminDto.getEmployments().forEach(employmentId -> employmentList.add(employmentRepository.findById(employmentId).orElseThrow(EntityNotFoundException::new)));

        List<Attachment> attachmentList = new ArrayList<>();
        companyAdminDto.getAttachments().forEach(attachmentId -> attachmentList.add(attachmentRepository.findById(attachmentId).orElseThrow(EntityNotFoundException::new)));

        List<ToolEvent> toolEventList = new ArrayList<>();
        companyAdminDto.getToolEvents().forEach(toolEventId -> toolEventList.add(toolEventRepository.findById(toolEventId).orElseThrow(EntityNotFoundException::new)));

        CompanyAdmin companyAdmin = new CompanyAdmin();
        companyAdmin.setId(companyAdminDto.getId());
        companyAdmin.setFirstName(companyAdminDto.getFirstName());
        companyAdmin.setLastName(companyAdminDto.getLastName());
        companyAdmin.setUsername(companyAdminDto.getUsername());
        companyAdmin.setPassword(companyAdminDto.getPassword());
        companyAdmin.setEmail(companyAdminDto.getEmail());
        companyAdmin.setPhone(companyAdminDto.getPhone());
        companyAdmin.setPesel(companyAdminDto.getPesel());
        companyAdmin.setUnavailabilities(unavailabilityList);
        companyAdmin.setNotifications(notificationList);
        companyAdmin.setEmployeeComments(employeeCommentsList);
        companyAdmin.setElementEvents(elementEventList);
        companyAdmin.setEmployments(employmentList);
        companyAdmin.setAttachments(attachmentList);
        companyAdmin.setToolEvents(toolEventList);

        return companyAdmin;
    }
}
