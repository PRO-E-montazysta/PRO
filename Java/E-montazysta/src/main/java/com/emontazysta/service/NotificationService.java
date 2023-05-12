package com.emontazysta.service;

import com.emontazysta.enums.NotificationType;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    void createNotification(List<AppUser> notifiedEmployees, Long triggerId, NotificationType triggerType);

    List<NotificationDto> getAllNotReaded(Long employeeId);

    NotificationDto update(Long notificationId);

    List<AppUser> createListOfEmployeesToNotificate(List<AppUser> allByRole);
}
