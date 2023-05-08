package com.emontazysta.service.impl;

import com.emontazysta.enums.NotificationType;
import com.emontazysta.mapper.NotificationMapper;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.Notification;
import com.emontazysta.model.dto.NotificationDto;
import com.emontazysta.repository.AppUserRepository;
import com.emontazysta.repository.NotificationRepository;
import com.emontazysta.repository.OrderRepository;
import com.emontazysta.repository.OrderStageRepository;
import com.emontazysta.service.NotificationService;
import com.emontazysta.service.OrderStageService;
import com.emontazysta.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
        Notification notification = Notification.builder()
                .createdAt(LocalDateTime.now())
                .notificationType(triggerType)
                .content(triggerType.getMessage())
                .createdBy(authUtils.getLoggedUser())
                .notifiedEmployees(notifiedEmployees)
                .build();

        switch (triggerType) {
            case ORDER_CREATED:
            case FOREMAN_ASSIGNMENT:
                notification.setOrder(orderRepository.findById(triggerId).orElseThrow(EntityNotFoundException::new));
                break;
            case FITTER_ASSIGNMENT:
                notification.setOrderStage(orderStageRepository.findById(triggerId).orElseThrow(EntityNotFoundException::new));
                break;
        }

        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationDto> getAllNotReaded(Long employeeId) {
        List<NotificationDto> notificationDtos = new ArrayList<>();

        List<Notification> notifications = notificationRepository.getAllByNotifiedEmployeesContaining(appUserService.getById(employeeId));

        if (!notifications.isEmpty()){
            notificationDtos = notifications.stream()
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
}
