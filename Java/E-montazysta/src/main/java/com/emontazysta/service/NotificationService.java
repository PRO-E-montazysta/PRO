package com.emontazysta.service;

import com.emontazysta.model.AppUser;
import com.emontazysta.model.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    void createNotification(String content, Long createdByUserId, List<AppUser> notifiedEmployees, Long orderStageId);
    void createNotification(Long orderId, String content, Long createdByUserId, List<AppUser> notifiedEmployees);

    List<NotificationDto> getAllNotReaded(Long employeeId);

    NotificationDto update(Long notificationId);
}
