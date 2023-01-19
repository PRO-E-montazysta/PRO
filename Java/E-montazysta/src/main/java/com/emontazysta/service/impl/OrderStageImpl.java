package com.emontazysta.service.impl;

import com.emontazysta.model.OrderStage;
import com.emontazysta.repository.OrderStageRepository;
import com.emontazysta.service.OrderStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderStageImpl implements OrderStageService {

    private final OrderStageRepository repository;

    @Override
    public List<OrderStage> getAll() {
        return repository.findAll();
    }

    @Override
    public OrderStage getById(Long id) {
        return repository.findById(id)
                         .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void add(OrderStage orderStage) {
        repository.save(orderStage);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public OrderStage update(Long id, OrderStage orderStage) {
        OrderStage orderStageDb = getById(id);
        orderStageDb.setName(orderStage.getName());
        orderStageDb.setStatus(orderStage.getStatus());
        orderStageDb.setPrice(orderStage.getPrice());
        orderStageDb.setOrder(orderStage.getOrder());
        orderStageDb.setPlannedEndDate(orderStage.getPlannedEndDate());
        orderStageDb.setStartDate(orderStage.getStartDate());
        orderStageDb.setEndDate(orderStage.getEndDate());
        orderStageDb.setPlannedDurationTime(orderStage.getPlannedDurationTime());
        orderStageDb.setPlannedFittersNumber(orderStage.getPlannedFittersNumber());
        orderStageDb.setMinimumImagesNumber(orderStage.getMinimumImagesNumber());

        return orderStageDb;
    }
}
