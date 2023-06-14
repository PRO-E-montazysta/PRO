package com.emontazysta.repository;

import com.emontazysta.model.AppUser;
import com.emontazysta.model.Unavailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UnavailabilityRepository extends JpaRepository<Unavailability, Long> {
    List<Unavailability> findAllByAssignedTo(AppUser assignedTo);
    List<Unavailability> findAllByUnavailableFromGreaterThanAndUnavailableFromLessThan(LocalDateTime startDate, LocalDateTime endDate);
    Optional<Unavailability> findByOrderStageIdAndAssignedTo(Long orderStageId, AppUser assignedTo);
}
