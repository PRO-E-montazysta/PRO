package com.emontazysta.repository;

import com.emontazysta.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByIdAndDeletedIsFalse(Long id);
    List<Company> findAllByDeletedIsFalse();
}
