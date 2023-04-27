package com.emontazysta.repository;

import com.emontazysta.model.ToolType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ToolTypeRepository extends JpaRepository<ToolType, Long> {

    Optional<ToolType> findByIdAndDeletedIsFalse(Long id);
    List<ToolType> findAllByDeletedIsFalse();
}
