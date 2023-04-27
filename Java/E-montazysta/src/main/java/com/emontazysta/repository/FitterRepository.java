package com.emontazysta.repository;

import com.emontazysta.model.Fitter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FitterRepository extends JpaRepository<Fitter, Long> {

    Optional<Fitter> findByIdAndDeletedIsFalse(Long id);
    List<Fitter> findAllByDeletedIsFalse();
}
