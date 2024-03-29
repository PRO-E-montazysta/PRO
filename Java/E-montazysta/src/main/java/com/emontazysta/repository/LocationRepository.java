package com.emontazysta.repository;

import com.emontazysta.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByIdAndDeletedIsFalse(Long id);
    List<Location> findAllByDeletedIsFalse();
}
