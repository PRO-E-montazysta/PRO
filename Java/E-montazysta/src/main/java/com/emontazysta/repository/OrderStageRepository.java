package com.emontazysta.repository;

import com.emontazysta.model.OrderStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderStageRepository extends JpaRepository<OrderStage, Long> {

    Optional<OrderStage> findByIdAndDeletedIsFalse(Long id);
    List<OrderStage> findAllByDeletedIsFalse();
}
