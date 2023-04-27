package com.emontazysta.repository;

import com.emontazysta.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByIdAndDeletedIsFalse(Long id);
    List<Notification> findAllByDeletedIsFalse();
}
