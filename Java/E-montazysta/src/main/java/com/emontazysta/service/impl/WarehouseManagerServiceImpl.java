package com.emontazysta.service.impl;

import com.emontazysta.model.WarehouseManager;
import com.emontazysta.repository.WarehouseManagerRepository;
import com.emontazysta.service.WarehouseManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseManagerServiceImpl implements WarehouseManagerService {

    private final WarehouseManagerRepository repository;

    @Override
    public List<WarehouseManager> getAll() {
        return repository.findAll();
    }

    @Override
    public WarehouseManager getById(Long id) {
        return repository.findById(id)
                         .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void add(WarehouseManager warehouseManager) {
        repository.save(warehouseManager);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
