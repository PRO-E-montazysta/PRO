package com.emontazysta.repository;

import com.emontazysta.model.ElementInWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ElementInWarehouseRepository  extends JpaRepository<ElementInWarehouse, Long> {

    Optional<ElementInWarehouse> findByIdAndDeletedIsFalse(Long id);
    List<ElementInWarehouse> findAllByDeletedIsFalse();
}
