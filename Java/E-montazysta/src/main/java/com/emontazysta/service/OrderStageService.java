package com.emontazysta.service;

import com.emontazysta.model.OrderStage;

import java.util.List;

public interface OrderStageService {

    List<OrderStage> getAll();
    OrderStage getById(Long id);
    void add(OrderStage orderStage);
    void delete(Long id);
    OrderStage update(Long id, OrderStage orderStage);
}
