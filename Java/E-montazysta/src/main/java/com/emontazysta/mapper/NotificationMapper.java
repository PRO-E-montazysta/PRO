package com.emontazysta.mapper;

import com.emontazysta.model.AppUser;
import com.emontazysta.model.Notification;
import com.emontazysta.model.dto.NotificationDto;
import com.emontazysta.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotificationMapper {

    private final AppUserRepository appUserRepository;
    private final OrderStageRepository orderStageRepository;
    private final OrderRepository orderRepository;
    private final ToolEventRepository toolEventRepository;
    private final ElementEventRepository elementEventRepository;

    public NotificationDto toDto(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .notificationType(notification.getNotificationType())
                .content(notification.getContent())
                .subContent(notification.getSubContent())
                .createdAt(notification.getCreatedAt())
                .readAt(notification.getReadAt())
                .createdById(notification.getCreatedBy() == null ? null : notification.getCreatedBy().getId())
                .notifiedEmployeeId(notification.getNotifiedEmployee().getId())
                .orderStageId(notification.getOrderStage() == null ? null : notification.getOrderStage().getId())
                .orderId(notification.getOrder() == null ? null : notification.getOrder().getId())
                .toolEventId(notification.getToolEvent() == null ? null : notification.getToolEvent().getId())
                .elementEventId(notification.getElementEvent() == null ? null : notification.getElementEvent().getId())
                .deleted(notification.isDeleted())
                .build();
    }

    public Notification toEntity(NotificationDto notificationDto) {
        return Notification.builder()
                .id(notificationDto.getId())
                .notificationType(notificationDto.getNotificationType())
                .content(notificationDto.getContent())
                .subContent(notificationDto.getSubContent())
                .createdAt(notificationDto.getCreatedAt())
                .readAt(notificationDto.getReadAt())
                .createdBy(notificationDto.getCreatedById() == null ? null : appUserRepository.getReferenceById(notificationDto.getCreatedById()))
                .notifiedEmployee(notificationDto.getNotifiedEmployeeId() == null ? null : appUserRepository.getReferenceById(notificationDto.getNotifiedEmployeeId()))
                .orderStage(notificationDto.getOrderStageId() == null ? null : orderStageRepository.getReferenceById(notificationDto.getOrderStageId()))
                .order(notificationDto.getOrderId() == null ? null : orderRepository.getReferenceById(notificationDto.getOrderId()))
                .toolEvent(notificationDto.getToolEventId() == null ? null : toolEventRepository.getReferenceById(notificationDto.getToolEventId()))
                .elementEvent(notificationDto.getElementEventId() == null ? null : elementEventRepository.getReferenceById(notificationDto.getElementEventId()))
                .deleted(notificationDto.isDeleted())
                .build();
    }
}
