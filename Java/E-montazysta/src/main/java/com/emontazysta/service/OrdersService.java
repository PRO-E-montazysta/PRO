package com.emontazysta.service;

import com.emontazysta.model.Orders;

import java.util.List;

public interface OrdersService {

    List<Orders> getAll();
    Orders getById(Long id);
    void add(Orders orders);
    void delete(Long id);
    void update(Long id, Orders orders);
}
