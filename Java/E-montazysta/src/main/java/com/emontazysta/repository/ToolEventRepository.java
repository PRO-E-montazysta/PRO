package com.emontazysta.repository;

import com.emontazysta.model.ToolEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ToolEventRepository extends JpaRepository<ToolEvent, Long> {

    Optional<ToolEvent> findByIdAndDeletedIsFalse(Long id);
    List<ToolEvent> findAllByDeletedIsFalse();
}
