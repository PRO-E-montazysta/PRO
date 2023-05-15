package com.emontazysta.repository;

import com.emontazysta.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findByIdAndDeletedIsFalse(Long id);
    List<Manager> findAllByDeletedIsFalse();
}
