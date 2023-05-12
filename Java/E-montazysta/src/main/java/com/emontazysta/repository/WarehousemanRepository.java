package com.emontazysta.repository;

import com.emontazysta.model.Warehouseman;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WarehousemanRepository extends JpaRepository<Warehouseman, Long> {

    Optional<Warehouseman> findByIdAndDeletedIsFalse(Long id);
    List<Warehouseman> findAllByDeletedIsFalse();
}
