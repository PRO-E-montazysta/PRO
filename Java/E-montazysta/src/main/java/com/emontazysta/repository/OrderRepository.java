package com.emontazysta.repository;

import com.emontazysta.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
