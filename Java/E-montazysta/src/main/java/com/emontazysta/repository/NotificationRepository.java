package com.emontazysta.repository;

import com.emontazysta.model.AppUser;
import com.emontazysta.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> getAllByNotifiedEmployeesContaining(AppUser notifiedEmployee);


}
