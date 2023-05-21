package com.emontazysta.repository;

import com.emontazysta.model.ElementsPlannedNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElementsPlannedNumberRepository extends JpaRepository<ElementsPlannedNumber, Long> {

    Optional<ElementsPlannedNumber> findByIdAndDeletedIsFalse(Long id);
    List<ElementsPlannedNumber> findAllByDeletedIsFalse();
}
