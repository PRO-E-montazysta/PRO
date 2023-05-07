package com.emontazysta.service.impl;

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

    @Override
    public void createNotification(String content, Long createdByUserId, List<AppUser> notifiedEmployees, Long orderStageId) {
        Notification notification = Notification.builder()
                .createdAt(LocalDateTime.now())
                .content(content)
                .createdBy(appUserService.getById(createdByUserId))
                .notifiedEmployees(notifiedEmployees)
                .orderStage(orderStageRepository.findById(orderStageId).orElseThrow(EntityNotFoundException::new))
                .build();
        notificationRepository.save(notification);
    }

    @Override
    public void createNotification(Long orderId, String content, Long createdByUserId, List<AppUser> notifiedEmployees) {
        Notification notification = Notification.builder()
                .createdAt(LocalDateTime.now())
                .content(content)
                .createdBy(appUserService.getById(createdByUserId))
                .notifiedEmployees(notifiedEmployees)
                .order(orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new))
                .build();
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
