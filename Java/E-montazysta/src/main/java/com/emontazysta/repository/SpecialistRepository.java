package com.emontazysta.repository;

import com.emontazysta.model.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpecialistRepository extends JpaRepository<Specialist, Long> {

    Optional<Specialist> findByIdAndDeletedIsFalse(Long id);
    List<Specialist> findAllByDeletedIsFalse();
}
