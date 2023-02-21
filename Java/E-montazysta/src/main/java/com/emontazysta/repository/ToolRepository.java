package com.emontazysta.repository;

import com.emontazysta.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<Tool, Long> {
    Tool findByCode(String code);

    Tool findAllByToolType_Id(long toolTypeId);

    Tool findAllByToolReleasesId(long toolReleasesId);
}
