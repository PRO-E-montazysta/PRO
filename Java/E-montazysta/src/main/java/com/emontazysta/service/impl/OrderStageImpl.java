package com.emontazysta.service.impl;

import com.emontazysta.model.OrderStage;
import com.emontazysta.repository.OrderStageRepository;
import com.emontazysta.service.OrderStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
