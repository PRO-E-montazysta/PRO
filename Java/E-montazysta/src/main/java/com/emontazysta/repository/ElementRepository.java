package com.emontazysta.repository;

import com.emontazysta.model.Element;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ElementRepository extends JpaRepository<Element,Long> {

    List<Element> findAllByDeletedIsFalse();
    Optional<Element> findByCodeAndDeletedIsFalse(String code);
    Optional<Element> findByIdAndDeletedIsFalse(Long id);
}
