package com.emontazysta.repository;

import com.emontazysta.model.CompanyAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyAdminRepository extends JpaRepository<CompanyAdmin, Long> {

    Optional<CompanyAdmin> findByIdAndDeletedIsFalse(Long id);
    List<CompanyAdmin> findAllByDeletedIsFalse();
}
