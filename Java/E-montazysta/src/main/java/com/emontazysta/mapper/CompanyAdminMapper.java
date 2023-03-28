package com.emontazysta.mapper;

import com.emontazysta.model.*;
import com.emontazysta.model.dto.CompanyAdminDto;
import com.emontazysta.model.searchcriteria.CompanyAdmin;
import com.emontazysta.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
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
                .unavailabilities(companyAdmin.getUnavailabilities().stream().map(Unavailability::getId).collect(Collectors.toList()))
                .notifications(companyAdmin.getNotifications().stream().map(Notification::getId).collect(Collectors.toList()))
                .employeeComments(companyAdmin.getEmployeeComments().stream().map(Comment::getId).collect(Collectors.toList()))
                .elementEvents(companyAdmin.getElementEvents().stream().map(ElementEvent::getId).collect(Collectors.toList()))
                .employments(companyAdmin.getEmployments().stream().map(Employment::getId).collect(Collectors.toList()))
                .attachments(companyAdmin.getAttachments().stream().map(Attachment::getId).collect(Collectors.toList()))
                .toolEvents(companyAdmin.getToolEvents().stream().map(ToolEvent::getId).collect(Collectors.toList()))
                .build();
    }

    public CompanyAdmin toEntity(CompanyAdminDto companyAdminDto) {

        List<Unavailability> unavailabilityList = new ArrayList<>();
        companyAdminDto.getUnavailabilities().forEach(unavailabilityId -> unavailabilityList.add(unavailabilityRepository.getReferenceById(unavailabilityId)));

        List<Notification> notificationList = new ArrayList<>();
        companyAdminDto.getNotifications().forEach(notificationId -> notificationList.add(notificationRepository.getReferenceById(notificationId)));

        List<Comment> employeeCommentsList = new ArrayList<>();
        companyAdminDto.getEmployeeComments().forEach(commentId -> employeeCommentsList.add(commentRepository.getReferenceById(commentId)));

        List<ElementEvent> elementEventList = new ArrayList<>();
        companyAdminDto.getElementEvents().forEach(elementEventId -> elementEventList.add(elementEventRepository.getReferenceById(elementEventId)));

        List<Employment> employmentList = new ArrayList<>();
        companyAdminDto.getEmployments().forEach(employmentId -> employmentList.add(employmentRepository.getReferenceById(employmentId)));

        List<Attachment> attachmentList = new ArrayList<>();
        companyAdminDto.getAttachments().forEach(attachmentId -> attachmentList.add(attachmentRepository.getReferenceById(attachmentId)));

        List<ToolEvent> toolEventList = new ArrayList<>();
        companyAdminDto.getToolEvents().forEach(toolEventId -> toolEventList.add(toolEventRepository.getReferenceById(toolEventId)));

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
