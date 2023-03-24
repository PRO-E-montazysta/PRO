package com.emontazysta.service;

import com.emontazysta.model.dto.OrderStageDto;
import com.emontazysta.model.searchcriteria.OrdersStageSearchCriteria;

import java.security.Principal;
import java.util.List;

public interface OrderStageService {

    List<OrderStageDto> getAll();
    OrderStageDto getById(Long id);
    OrderStageDto add(OrderStageDto orderStage);
    void delete(Long id);
    OrderStageDto update(Long id, OrderStageDto orderStage);

    List<OrderStageDto> getFilteredOrders(OrdersStageSearchCriteria ordersStageSearchCriteria, Principal principal);
}
