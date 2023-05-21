package com.emontazysta.repository;

import com.emontazysta.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByIdAndDeletedIsFalse(Long id);
    List<Client> findAllByDeletedIsFalse();
}
