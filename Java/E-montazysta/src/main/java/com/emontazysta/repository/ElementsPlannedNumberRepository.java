package com.emontazysta.repository;

import com.emontazysta.model.ElementsPlannedNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElementsPlannedNumberRepository extends JpaRepository<ElementsPlannedNumber, Long> {
}
