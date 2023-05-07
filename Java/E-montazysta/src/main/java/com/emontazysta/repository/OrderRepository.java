package com.emontazysta.repository;

import com.emontazysta.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface OrderRepository extends JpaRepository<Orders, Long> {

    public Optional<Orders> findById(Long id);

}
