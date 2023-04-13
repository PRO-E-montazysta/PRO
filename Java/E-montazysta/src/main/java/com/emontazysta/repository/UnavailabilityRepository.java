package com.emontazysta.repository;

import com.emontazysta.model.AppUser;
import com.emontazysta.model.Unavailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnavailabilityRepository extends JpaRepository<Unavailability, Long> {

    List<Unavailability> findAllByAssignedTo(AppUser assignedTo);
}
