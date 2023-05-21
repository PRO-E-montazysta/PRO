package com.emontazysta.repository;

import com.emontazysta.model.SalesRepresentative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SalesRepresentativeRepository extends JpaRepository<SalesRepresentative, Long> {

    Optional<SalesRepresentative> findByIdAndDeletedIsFalse(Long id);
    List<SalesRepresentative> findAllByDeletedIsFalse();
}
