package com.emontazysta.service;

import com.emontazysta.model.dto.OrdersDto;
import com.emontazysta.model.searchcriteria.OrdersSearchCriteria;

import java.util.List;

public interface OrdersService {

    List<OrdersDto> getAll();
    OrdersDto getById(Long id);
    OrdersDto add(OrdersDto orders);
    void delete(Long id);
    OrdersDto update(Long id, OrdersDto orders);

    public List<OrdersDto> getFilteredOrders(OrdersSearchCriteria ordersSearchCriteria);
}
