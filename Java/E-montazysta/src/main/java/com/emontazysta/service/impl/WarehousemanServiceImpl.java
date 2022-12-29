package com.emontazysta.service.impl;

import com.emontazysta.model.Warehouseman;
import com.emontazysta.repository.WarehousemanRepository;
import com.emontazysta.service.WarehousemanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehousemanServiceImpl implements WarehousemanService {

    private final WarehousemanRepository repository;

    @Override
    public List<Warehouseman> getAll() {
        return repository.findAll();
    }

    @Override
    public Warehouseman getById(Long id) {
        return repository.findById(id)
                         .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void add(Warehouseman warehouseman) {
        repository.save(warehouseman);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
