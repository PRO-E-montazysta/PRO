package com.emontazysta.service;

import com.emontazysta.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAll();
    Order getById(Long id);
    void add(Order order);
    void delete(Long id);
    void edit(Long id, Order order);
}
