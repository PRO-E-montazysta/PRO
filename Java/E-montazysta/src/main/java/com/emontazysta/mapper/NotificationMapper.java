package com.emontazysta.mapper;

import com.emontazysta.model.AppUser;
import com.emontazysta.model.Notification;
import com.emontazysta.model.dto.NotificationDto;
import com.emontazysta.repository.AppUserRepository;
import com.emontazysta.repository.OrderRepository;
import com.emontazysta.repository.OrderStageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotificationMapper {

    private final AppUserRepository appUserRepository;
    private final OrderStageRepository orderStageRepository;
    private final OrderRepository orderRepository;

    public NotificationDto toDto(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .notificationType(notification.getNotificationType())
                .content(notification.getContent())
                .createdAt(notification.getCreatedAt())
                .readAt(notification.getReadAt())
                .createdById(notification.getCreatedBy() == null ? null : notification.getCreatedBy().getId())
                .notifiedEmployees(notification.getNotifiedEmployees().stream().map(AppUser::getId).collect(Collectors.toList()))
                .orderStageId(notification.getOrderStage() == null ? null : notification.getOrderStage().getId())
                .orderId(notification.getOrder() == null ? null : notification.getOrder().getId())
                .build();
    }

    public Notification toEntity(NotificationDto notificationDto) {

        List<AppUser> appUserList = new ArrayList<>();
        notificationDto.getNotifiedEmployees().forEach(appUserId -> appUserList.add(appUserRepository.getReferenceById(appUserId)));

        return Notification.builder()
                .id(notificationDto.getId())
                .notificationType(notificationDto.getNotificationType())
                .content(notificationDto.getContent())
                .createdAt(notificationDto.getCreatedAt())
                .readAt(notificationDto.getReadAt())
                .createdBy(notificationDto.getCreatedById() == null ? null : appUserRepository.getReferenceById(notificationDto.getCreatedById()))
                .notifiedEmployees(appUserList)
                .orderStage(notificationDto.getOrderStageId() == null ? null : orderStageRepository.getReferenceById(notificationDto.getOrderStageId()))
                .order(notificationDto.getOrderId() == null ? null : orderRepository.getReferenceById(notificationDto.getOrderId()))
                .build();
    }
}
