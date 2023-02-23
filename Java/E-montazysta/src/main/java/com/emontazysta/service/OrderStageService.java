package com.emontazysta.service;

import com.emontazysta.model.dto.OrderStageDto;

import java.util.List;

public interface OrderStageService {

    List<OrderStageDto> getAll();
    OrderStageDto getById(Long id);
    OrderStageDto add(OrderStageDto orderStage);
    void delete(Long id);
    OrderStageDto update(Long id, OrderStageDto orderStage);
}
