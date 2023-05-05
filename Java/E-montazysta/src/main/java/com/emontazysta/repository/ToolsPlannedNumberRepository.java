package com.emontazysta.repository;

import com.emontazysta.model.ToolsPlannedNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolsPlannedNumberRepository extends JpaRepository<ToolsPlannedNumber, Long> {
}
