package com.emontazysta.service;

import com.emontazysta.model.dto.OrdersDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrdersService {

    List<OrdersDto> getAll();
    OrdersDto getById(Long id);
    OrdersDto add(OrdersDto orders);
    void delete(Long id);
    OrdersDto update(Long id, OrdersDto orders);

    public Page<OrdersDto> getFilteredOrders(OrdersPage ordersPage, OrdersSearchCriteria ordersSearchCriteria);
}
