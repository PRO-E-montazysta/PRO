package com.emontazysta.repository;

import com.emontazysta.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Optional<Warehouse> findByIdAndDeletedIsFalse(Long id);
    List<Warehouse> findAllByDeletedIsFalse();
}
