package com.emontazysta.repository;

import com.emontazysta.model.WarehouseManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WarehouseManagerRepository extends JpaRepository<WarehouseManager, Long> {

    Optional<WarehouseManager> findByIdAndDeletedIsFalse(Long id);
    List<WarehouseManager> findAllByDeletedIsFalse();
}
