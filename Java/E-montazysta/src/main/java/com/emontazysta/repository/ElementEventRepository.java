package com.emontazysta.repository;

import com.emontazysta.model.ElementEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ElementEventRepository extends JpaRepository<ElementEvent, Long> {

    Optional<ElementEvent> findByIdAndDeletedIsFalse(Long id);
    List<ElementEvent> findAllByDeletedIsFalse();
}
