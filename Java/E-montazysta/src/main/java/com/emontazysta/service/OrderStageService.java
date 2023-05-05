package com.emontazysta.service;

import com.emontazysta.model.dto.OrderStageDto;
import com.emontazysta.model.dto.OrderStageWithToolsAndElementsDto;
import com.emontazysta.model.searchcriteria.OrdersStageSearchCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

public interface OrderStageService {

    List<OrderStageDto> getAll();

    OrderStageDto getById(Long id);

    OrderStageDto add(OrderStageDto orderStageDto);

    @Transactional
    OrderStageDto addWithToolsAndElements(OrderStageWithToolsAndElementsDto orderStageDto);

    void delete(Long id);

    OrderStageDto update(Long id, OrderStageWithToolsAndElementsDto orderStage);

    List<OrderStageDto> getFilteredOrders(OrdersStageSearchCriteria ordersStageSearchCriteria, Principal principal);

    OrderStageDto releaseTools(Long id, List<String> toolCodes);
    OrderStageDto returnTools(Long id, List<String> toolCodes);
}
