package com.emontazysta.service;

import com.emontazysta.model.dto.OrdersDto;

import java.util.List;

public interface OrdersService {

    List<OrdersDto> getAll();
    OrdersDto getById(Long id);
    OrdersDto add(OrdersDto orders);
    void delete(Long id);
    OrdersDto update(Long id, OrdersDto orders);
}
