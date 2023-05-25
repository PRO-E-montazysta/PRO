package com.emontazysta.repository;

import com.emontazysta.model.ElementReturnRelease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ElementReturnReleaseRepository extends JpaRepository<ElementReturnRelease, Long> {

    Optional<ElementReturnRelease> findByIdAndDeletedIsFalse(Long id);
    List<ElementReturnRelease> findAllByDeletedIsFalse();
}
