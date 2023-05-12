package com.emontazysta.repository;

import com.emontazysta.model.Foreman;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ForemanRepository extends JpaRepository<Foreman, Long> {

    Optional<Foreman> findByIdAndDeletedIsFalse(Long id);
    List<Foreman> findAllByDeletedIsFalse();
}
