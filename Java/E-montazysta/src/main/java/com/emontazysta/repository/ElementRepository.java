package com.emontazysta.repository;

import com.emontazysta.model.Element;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElementRepository extends JpaRepository<Element,Long> {
    Element findByCode(String code);
}
