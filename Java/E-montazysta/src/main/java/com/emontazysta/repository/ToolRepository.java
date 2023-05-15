package com.emontazysta.repository;

import com.emontazysta.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ToolRepository extends JpaRepository<Tool, Long> {

    Optional<Tool> findByIdAndDeletedIsFalse(Long id);
    Tool findByCode(String code);

    Tool findAllByToolType_Id(long toolTypeId);

    Tool findAllByToolReleasesId(long toolReleasesId);
    List<Tool> findAllByDeletedIsFalse();
}
