package com.emontazysta.service.impl;

import com.emontazysta.model.Warehouse;
import com.emontazysta.repository.WarehouseRepository;
import com.emontazysta.service.WarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository repository;

    @Override
    public List<Warehouse> getAll() {
        return repository.findAll();
    }

    @Override
    public Warehouse getById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void add(Warehouse warehouse) {
        repository.save(warehouse);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Long id, Warehouse warehouse) {
        Warehouse updatedWarehouse = this.getById(id);
        updatedWarehouse.setName(warehouse.getName());
        updatedWarehouse.setDescription(warehouse.getDescription());
        updatedWarehouse.setOpeningHours(warehouse.getOpeningHours());

        repository.save(updatedWarehouse);
    }
}
