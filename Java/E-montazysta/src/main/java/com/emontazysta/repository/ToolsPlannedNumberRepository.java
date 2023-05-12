package com.emontazysta.repository;

import com.emontazysta.model.ToolsPlannedNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToolsPlannedNumberRepository extends JpaRepository<ToolsPlannedNumber, Long> {

    Optional<ToolsPlannedNumber> findByIdAndDeletedIsFalse(Long id);
    List<ToolsPlannedNumber> findAllByDeletedIsFalse();
}
