package com.emontazysta.repository;

import com.emontazysta.model.AppUser;
import com.emontazysta.model.Unavailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UnavailabilityRepository extends JpaRepository<Unavailability, Long> {

    Optional<Unavailability> findByIdAndDeletedIsFalse(Long id);
    List<Unavailability> findAllByAssignedTo(AppUser assignedTo);
    List<Unavailability> findAllByDeletedIsFalse();
}
