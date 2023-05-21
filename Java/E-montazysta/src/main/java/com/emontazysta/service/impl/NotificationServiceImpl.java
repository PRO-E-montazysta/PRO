package com.emontazysta.service.impl;

import com.emontazysta.enums.NotificationType;
import com.emontazysta.mapper.NotificationMapper;
import com.emontazysta.model.*;
import com.emontazysta.model.dto.NotificationDto;
import com.emontazysta.repository.NotificationRepository;
import com.emontazysta.repository.OrderRepository;
import com.emontazysta.repository.OrderStageRepository;
import com.emontazysta.service.NotificationService;
import com.emontazysta.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final AppUserServiceImpl appUserService;
    private final OrderStageRepository orderStageRepository;
    private final OrderRepository orderRepository;
    private final AuthUtils authUtils;

    @Override
    public void createNotification(List<AppUser> notifiedEmployees, Long triggerId, NotificationType triggerType) {
        for(AppUser appUser : notifiedEmployees) {
            Notification notification = Notification.builder()
                    .createdAt(LocalDateTime.now())
                    .notificationType(triggerType)
                    .content(triggerType.getMessage())
                    .notifiedEmployee(appUser)
                    .createdBy(authUtils.getLoggedUser())
                    .build();

            switch (triggerType) {
                case ORDER_CREATED:
                case ACCEPT_ORDER:
                case FOREMAN_ASSIGNMENT:
                    Orders order = orderRepository.findById(triggerId).orElseThrow(EntityNotFoundException::new);
                    notification.setOrder(order);
                    notification.setSubContent(order.getName());
                    break;
                case FITTER_ASSIGNMENT:
                    OrderStage orderStage = orderStageRepository.findById(triggerId).orElseThrow(EntityNotFoundException::new);
                    notification.setOrderStage(orderStage);
                    notification.setSubContent(orderStage.getName());
                    break;
                case AD_HOC_CREATED:
                    //TODO On DemandAdHoc implementation
                    break;
                /*case TOOL_EVENT:
                    ToolEvent toolEvent = toolEventRepository.findById(triggerId).orElseThrow(EntityNotFoundException::new);
                    notification.setToolEvent(toolEvent);
                    notification.setSubContent("Usterka " + toolEvent.getTool().getName());
                    break;
                case ELEMENT_EVENT:
                    ElementEvent elementEvent = elementEventRepository.findById(triggerId).orElseThrow(EntityNotFoundException::new);
                    notification.setElementEvent(elementEvent);
                    notification.setSubContent("Usterka " + elementEvent.getElement().getName());
                    break;*/
            }

            notificationRepository.save(notification);
        }
    }

    @Override
    public List<NotificationDto> getAllNotReaded(Long employeeId) {
        List<NotificationDto> notificationDtos = new ArrayList<>();

        List<Notification> notifications = notificationRepository.getAllByNotifiedEmployee(appUserService.getById(employeeId));

        if (!notifications.isEmpty()){
            notificationDtos = notifications.stream()
                    .filter(notification -> notification.getReadAt() == null)
                    .map(notification -> notificationMapper.toDto(notification))
                    .collect(Collectors.toList());
        }

        return notificationDtos;
    }

    @Override
    public NotificationDto update(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow
                (EntityNotFoundException::new);
        notification.setReadAt(LocalDateTime.now());

        return notificationMapper.toDto(notificationRepository.save(notification));
    }

    @Override
    public List<AppUser> createListOfEmployeesToNotificate(List<AppUser> allByRole) {
        Long companyId = authUtils.getLoggedUserCompanyId();
        List<AppUser> filteredUsers = new ArrayList<>();

        for (AppUser user : allByRole) {
            Optional<Employment> takingEmployment = user.getEmployments().stream()
                    .filter(employment -> employment.getDateOfDismiss() == null)
                    .findFirst();
            if (takingEmployment.isPresent()) {
                Long employeeCompanyId = takingEmployment.get().getCompany().getId();
                if (employeeCompanyId.equals(companyId)) {
                    filteredUsers.add(user);
                }
            }
        }
        return filteredUsers;
    }
}
