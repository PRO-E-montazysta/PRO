package com.emontazysta.repository;

import com.emontazysta.model.Employment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmploymentRepository extends JpaRepository<Employment, Long> {

    Optional<Employment> findByIdAndDeletedIsFalse(Long id);
    Employment findByEmployeeIdAndDateOfDismissIsNull(Long employeeId);
    List<Employment> findAllByDeletedIsFalse();
}
