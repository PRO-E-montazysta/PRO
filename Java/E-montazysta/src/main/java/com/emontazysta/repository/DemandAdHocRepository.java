package com.emontazysta.repository;

import com.emontazysta.model.DemandAdHoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DemandAdHocRepository extends JpaRepository<DemandAdHoc, Long> {

    List<DemandAdHoc> findAllByDeletedIsFalse();
    Optional<DemandAdHoc> findByIdAndDeletedIsFalse(Long id);
}
