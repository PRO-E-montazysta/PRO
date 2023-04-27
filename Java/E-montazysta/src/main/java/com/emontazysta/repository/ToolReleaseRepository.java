package com.emontazysta.repository;

import com.emontazysta.model.ToolRelease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ToolReleaseRepository extends JpaRepository<ToolRelease, Long> {

    Optional<ToolRelease> findByIdAndDeletedIsFalse(Long id);
    List<ToolRelease> findAllByDeletedIsFalse();
}
